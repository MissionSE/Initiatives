sys.path.append("/home/autotest/AutomatedTest/Scenario/sikuli/Scenario.sikuli")

import Firefox
import Ciwi
reload(Firefox)
reload(Ciwi)

def testCiwiInstall():
    
    Ciwi.startInstall()

    Ciwi.enterRemoteLoginCredentials()

    Ciwi.enterInstallPath("/home/autotest/ciwi")

    Ciwi.enterSiteSpecificQuestions()

    Ciwi.enterPhysicalHosts()

    Ciwi.submitTransaction()

    Ciwi.gotoAdsComponentStatusByHost()
    
    wait("CiwiRhel1Installed.png", 20)


def testCiwiUninstall():
    
    Ciwi.startUninstall()

    Ciwi.enterRemoteLoginCredentials()

    Ciwi.enterSiteSpecificQuestions()

    Ciwi.enterPhysicalHosts()

    Ciwi.submitTransaction()

    Ciwi.gotoAdsComponentStatusByHost()    
    
    wait(Pattern("CiwiRhel1Uninstalled.png").exact(), 20)


# Start of main routine
Firefox.start()

Firefox.navigate("rhelserv1/ciwi/ciwi")

Ciwi.doLogin()

Ciwi.doSelectDomain()

Ciwi.doMainMenuComponents()

Ciwi.gotoAdsComponentStatusByHost()

testCiwiInstall()

testCiwiUninstall()
  
Ciwi.doLogout()

Firefox.quit()



  