from sikuli import *

def start():
    click(Pattern("FirefoxLaunchIcon.png").similar(0.80))

def navigate(url):
    doubleClick(Pattern("FirefoxAddressBar.png").similar(0.80).targetOffset(-49,2))
    type(url + "\n")

def quit():
    click(Pattern("FirefoxFileMenu.png").similar(0.95).targetOffset(-1,10))
    click(Pattern("FirefoxQuitFromMenu.png").targetOffset(-13,31))
    
    
    
    
    
    