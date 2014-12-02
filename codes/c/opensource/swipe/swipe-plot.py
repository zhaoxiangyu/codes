#!/usr/bin/env python

import swipe
import matplotlib.pyplot as plt
    
sw = swipe.Swipe('king.wav')
plt.plot(sw.t,sw.p)
plt.show()
#print swipe.Swipe('king.wav').regress()
