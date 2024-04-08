import random
import argparse
import matplotlib.pyplot as plt

parser = argparse.ArgumentParser()
parser.add_argument('filename', type=str)
parser.add_argument('--P1', nargs=2, type=int, default=[100, 100])
parser.add_argument('--P2', nargs=2, type=int, default=[100, 500])
parser.add_argument('--P3', nargs=2, type=int, default=[700, 300])
parser.add_argument('-i', '--iterations', type=int, default=1000000)
parser.add_argument('-s', '--size', nargs=2, type=int, default=[800, 600])
parser.add_argument('-v', '--verbose', action='store_true')
parser.add_argument('-cs', '--color_sierpinskitriangle', default='blue')
parser.add_argument('-cb', '--color_background', default='red')
parser.add_argument('-ct', '--color_text', default='yellow')
parser.add_argument('-axis', '--remove_axis', action='store_true')

args = parser.parse_args()

if args.verbose:
    print('starting program')

plt.figure(figsize=(args.size[0]/10, args.size[1]/10), dpi=10, facecolor=args.color_background)
plt.annotate('Martin Bierbaumer', xy=(400, 300), fontsize=100, color=args.color_text)

if args.remove_axis:
    plt.axis('off')

if args.verbose:
    print('generating points')
point = args.P1
pointsx, pointsy = [], []
for _ in range(args.iterations):
    P = random.choice([args.P1, args.P2, args.P3])
    point = ((point[0] + P[0]) / 2, (point[1] + P[1]) / 2)
    pointsx.append(point[0])
    pointsy.append(point[1])

if args.verbose:
    print('painting picture')
plt.scatter(pointsx, pointsy, color=args.color_sierpinskitriangle)

plt.savefig(args.filename, dpi=10)
plt.show()
