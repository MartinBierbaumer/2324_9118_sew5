import argparse

def load_labyrinth(path):



if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="calculate number of ways through a labyrinth")
    parser.add_argument("filename", help="file containing the labyrinth to solve")
    parser.add_argument("-x", "--xstart", type=int, default=0, help="x-coordinate to start")
    parser.add_argument("-y", "--ystart", type=int, default=0, help="y-coordinate to start")
    parser.add_argument("-p", "--print", action="store_true", help="print output of every solution")
    parser.add_argument("-t", "--time", action="store_true", help="print total calculation time (in milliseconds)")
    parser.add_argument("-d", "--delay", type=int, default=0, help="delay after printing a solution (in milliseconds)")

    args = parser.parse_args()

    lab = load_labyrinth(args.filename)

