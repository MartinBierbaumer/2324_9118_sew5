import argparse
from subprocess import Popen, PIPE

parser = argparse.ArgumentParser(description="calculate number of ways through a labyrinth")
parser.add_argument("directory", help="directory containing git repository")

args = parser.parse_args()

git_log = ["git", "log", "--pretty=format:%ad", "--date=format-local:%a-%H-%M"]
process = Popen(git_log, stdout=PIPE, stderr=PIPE, text=True)
out, err = process.communicate()

