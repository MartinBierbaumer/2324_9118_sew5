import argparse
import math
from collections import Counter
from subprocess import Popen, PIPE
import matplotlib.pyplot as plt

parser = argparse.ArgumentParser(description="calculate number of ways through a labyrinth")
parser.add_argument("directory", help="directory containing git repository")

args = parser.parse_args()

git_log = ["git", "log", "--pretty=format:%ad", "--date=format-local:%a-%H-%M"]
process = Popen(git_log, stdout=PIPE, stderr=PIPE, text=True)
out, err = process.communicate()

amount = Counter()
commits = 0
for date in out.splitlines():
    split = date.split("-")
    amount[(split[0], math.floor((int(split[1]) + int(split[2]) / 60) * 2) / 2)] += 1
    commits += 1

weekdays = ["", "Mo", "Tu", "We", "Th", "Fr", "Sa", "Su", ""]

data = {"x": [], "y": [], "sizes": []}
for day, time in amount:
    data["x"].append(time)
    data["y"].append(weekdays.index(day))
    data["sizes"].append(100 + 25 * amount[(day, time)])

plt.ylabel('Tag')
plt.scatter(data['x'], data['y'], s=data['sizes'], alpha=0.5)
plt.yticks(range(len(weekdays)), labels=weekdays)

plt.xlabel('Zeit')
plt.xticks(range(0, 25, 2))
plt.title(f'Martin Bierbaumer: {commits} commits')
plt.grid(True, which="major", axis="y", linestyle="-", linewidth=2, color='black')

plt.savefig("statistic.png", dpi=72)
plt.show()
