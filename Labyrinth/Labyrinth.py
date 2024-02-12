import argparse
import time

def load_labyrinth(path):
    with open(path, 'r') as file:
        return [list(i[:-1]) for i in file]

def display(lab, path):
    for c in path[:-1]:
        lab[c[0]][c[1]] = "X"
    for line in lab:
        print(line)
    print()
    for c in path[:-1]:
        lab[c[0]][c[1]] = " "

def suche_alle(lab, path, pr, delay):
    """
    >>> suche_alle(["#####", "#   #", "#   #", "###A#"])
    4
    """
    if lab[path[-1][0]][path[-1][1]] == "A":
        if print:
            display(lab, path)
            time.sleep(delay / 1000)
        return 1
    if lab[path[-1][0]][path[-1][1]] == "#":
        return 0
    if lab[path[-1][0]][path[-1][1]] == " ":
        count = 0
        for dir in [(1, 0), (0, 1), (-1, 0), (0, -1)]:
            next = (path[-1][0] + dir[0], path[-1][1] + dir[1])
            if next not in path:
                path.append(next)
                count += suche_alle(lab, path, pr, delay)
                del path[-1]
        return count


def solve(args):
    lab = load_labyrinth(args.filename)
    t = time.time()

    amount = suche_alle(lab, [(args.xstart, args.ystart)], args.print, args.delay)
    print(amount)

    if args.time:
        print(f'Verbrauchte Zeit: {time.time() - t:.2f}s')


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="calculate number of ways through a labyrinth")
    parser.add_argument("filename", help="file containing the labyrinth to solve")
    parser.add_argument("-x", "--xstart", type=int, default=0, help="x-coordinate to start")
    parser.add_argument("-y", "--ystart", type=int, default=0, help="y-coordinate to start")
    parser.add_argument("-p", "--print", action="store_true", help="print output of every solution")
    parser.add_argument("-t", "--time", action="store_true", help="print total calculation time (in milliseconds)")
    parser.add_argument("-d", "--delay", type=int, default=0, help="delay after printing a solution (in milliseconds)")

    args = parser.parse_args()

    solve(args)

