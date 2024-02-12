import math
import matplotlib.pyplot as plt
PI = math.pi
CNT = 1024
X = [(2 * i - CNT) / CNT * PI for i in range(CNT)] # CNT Werte von -pi ... +pi
C = [math.cos(x) for x in X] # CNT cosinuswerte für x von -pi ... +pi
S = [math.sin(x) for x in X]
plt.figure(figsize=(10,6), dpi=80)
plt.xlim(min(X)*1.1, max(X)*1.1)
plt.ylim(min(C)*1.1, max(C)*1.1)
plt.xticks([-PI, -PI/2, 0, PI/2, PI], [r'$-\pi$', r'$-\pi/2$', r'$0$', r'$+\pi/2$', r'$+\pi$'])
plt.yticks([-1, 0, +1], [r'$-1$', r'$0$', r'$+1$'])
plt.plot(X, C, color="blue", linewidth=2.5, linestyle="-.", label="cosine")
plt.plot(X, S, color="red", linewidth=2.5, linestyle=":", label="sine")
plt.legend(loc='upper left', frameon=False)
ax = plt.gca()
ax.spines['right'].set_color('none')
ax.spines['top'].set_color('none')
ax.xaxis.set_ticks_position('bottom')
ax.spines['bottom'].set_position(('data',0))
ax.yaxis.set_ticks_position('left')
ax.spines['left'].set_position(('data',0))
ax.set_title("Plot von Martin Bierbaumer, HTL3R")
t = 2*PI/3
plt.plot([t,t],[0,math.cos(t)], color ='blue', linewidth=2.5, linestyle="--")
plt.scatter([t,],[math.cos(t),], 50, color ='blue')
plt.annotate(r'$\sin(\frac{2\pi}{3})=\frac{\sqrt{3}}{2}$',xy=(t, math.sin(t)), xycoords='data',xytext=(+10, +30), textcoords='offset points', fontsize=16,arrowprops=dict(arrowstyle="->", connectionstyle="arc3,rad=.2"))
plt.plot([t,t],[0,math.sin(t)], color ='red', linewidth=2.5, linestyle="--")
plt.scatter([t,],[math.sin(t),], 50, color ='red')
plt.annotate(r'$\cos(\frac{2\pi}{3})=-\frac{1}{2}$',xy=(t, math.cos(t)), xycoords='data',xytext=(-90, -50), textcoords='offset points', fontsize=16,arrowprops=dict(arrowstyle="->", connectionstyle="arc3,rad=.2"))
t = -PI/2
plt.plot([t,t],[0,math.cos(t)], color ='blue', linewidth=2.5, linestyle="--")
plt.scatter([t,],[math.cos(t),], 50, color ='blue')
plt.annotate(r'$\sin(\frac{-\pi}{2})=-1$',xy=(t, math.sin(t)), xycoords='data',xytext=(-90, -30), textcoords='offset points', fontsize=16,arrowprops=dict(arrowstyle="->", connectionstyle="arc3,rad=.2"))
plt.plot([t,t],[0,math.sin(t)], color ='red', linewidth=2.5, linestyle="--")
plt.scatter([t,],[math.sin(t),], 50, color ='red')
plt.annotate(r'$\cos(\frac{-\pi}{2})=0$',xy=(t, math.cos(t)), xycoords='data',xytext=(-90, 30), textcoords='offset points', fontsize=16,arrowprops=dict(arrowstyle="->", connectionstyle="arc3,rad=.2"))
for label in ax.get_xticklabels() + ax.get_yticklabels():
    print(label)
    label.set_fontsize(16)
    label.set_bbox(dict(facecolor='white', edgecolor='None', alpha=0.65))
    ax.set_axisbelow(True)
plt.annotate("", xytext=(0, 0), xy=(PI * 1.09, 0), arrowprops=dict(arrowstyle="->", color='black'))
plt.annotate("", xytext=(0, 0), xy=(0, 1.09), arrowprops=dict(arrowstyle="->", color='black'))
plt.annotate("", xytext=(0, 0), xy=(-PI * 1.09, 0), arrowprops=dict(arrowstyle="-", color='black'))
plt.annotate("", xytext=(0, 0), xy=(0, -1.09), arrowprops=dict(arrowstyle="-", color='black'))
plt.axis('off')
plt.savefig("plot1_Bierbaumer.png",dpi=72)
plt.show()