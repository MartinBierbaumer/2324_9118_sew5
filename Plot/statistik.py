import argparse
import math
from collections import defaultdict, Counter
from subprocess import Popen, PIPE

parser = argparse.ArgumentParser(description="calculate number of ways through a labyrinth")
parser.add_argument("directory", help="directory containing git repository")

args = parser.parse_args()

git_log = ["git", "log", "--pretty=format:%ad", "--date=format-local:%a-%H-%M"]
process = Popen(git_log, stdout=PIPE, stderr=PIPE, text=True)
out, err = process.communicate()

punkte = Counter()
commits = 0
for date in out.splitlines():
    split = date.split("-")
    punkte[(split[0], math.floor((int(split[1]) + int(split[2]) / 60) * 2))] += 1
    commits += 1

print(punkte)