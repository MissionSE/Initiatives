from sikuli import *

def start():
    click("1393439837193.png")

def navigate(url):
    click(Pattern("1393449125404.png").similar(0.80))
    

def quit():
    click(Pattern("1393441545842.png").similar(0.95).targetOffset(-1,10))
    click(Pattern("1393441906918.png").targetOffset(-13,31))
    
    
    
    
    
    