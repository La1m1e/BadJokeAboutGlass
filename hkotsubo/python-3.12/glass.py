#!/usr/bin/env python3

from time import sleep
from random import choice

class Glass:
    MAX_VOL = 100

    def __init__(self, volume=0):
        self.volume = min(volume, Glass.MAX_VOL)

    @property
    def volume(self):
        return self._volume

    @volume.setter
    def volume(self, volume):
        self._volume = max(volume, 0)

    def fill(self):
        self.volume = Glass.MAX_VOL

    def is_empty(self):
        return self.volume == 0

class Intern:
    def fill(self, glass):
        glass.fill()

class User:
    def __init__(self):
        self.is_thirsty = True

    def drink(self, glass, volume=10):
        if volume >= glass.volume:
            glass.volume = 0
        else:
            glass.volume -= volume
        self.is_thirsty = False

    def work(self):
        sleep(1)
        self.is_thirsty = True


thirsty_levels = (10, 20, 30, 40, 50)
user = User()
glass = Glass()
hour = 8
while hour < 17:
    if user.is_thirsty:
        if glass.is_empty():
            intern = Intern()
            intern.fill(glass)
        user.drink(glass, choice(thirsty_levels))
    user.work()
    hour += 1
