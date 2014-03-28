
cmdLineApp = r'/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0/bin/java -Dfile.encoding=UTF-8 -classpath /home/autotest/REPO/Initiatives/AutomatedTest/DEVELOPMENT/JAVA/workspaces/CalendarDateTimeWidget:/home/autotest/REPO/Initiatives/AutomatedTest/DEVELOPMENT/JAVA/workspaces/org.eclipse.swt/org.eclipse.swt_3.100.1.v4234e.jar:/home/autotest/REPO/Initiatives/AutomatedTest/DEVELOPMENT/JAVA/workspaces/org.eclipse.swt.gtk.linux.x86/org.eclipse.swt.gtk.linux.x86_3.100.1.v4234e.jar calendarDateTimeWidget.CalendarDateTimeWidget'

openApp(cmdLineApp)

if (exists("CalendarDateTimeBanner.png",5)):
    click(Pattern("CalendarDateTimeBanner.png").targetOffset(-87,35))


count = 0
while not exists("FebruaryText.png") and count < 13:
    click(Pattern("CalendarDateTimeBanner.png").targetOffset(-87,35))
    count = count + 1
 
click(Pattern("CalendarDateTimeExitButton.png").targetOffset(100,-1))


        
    
        