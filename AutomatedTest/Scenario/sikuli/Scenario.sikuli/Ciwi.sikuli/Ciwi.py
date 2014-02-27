from sikuli import *

def doLogin():
    click("CiwiLogin.png")
    type("\tciwi\tasd123\n")

def doSelectDomain():
    wait("CiwiSelectDomain.png", 8)
    click("CiwiSelectDomain.png")
    click("CiwiDomainInnovationLab.png")
    type("\t\r")

def doMainMenuComponents():
    wait("CiwiMainMenu.png", 8)
    click("CiwiComponents.png")

def gotoAdsComponentStatusByHost():
    wait("CiwiComponentsScreen.png", 8)
    click(Pattern("CiwiModifyADS.png").targetOffset(34,41))
    wait("CiwiComponentStatus.png", 8)
    click("CiwiByHostTab.png")

def startInstall():
    wait(Pattern("CiwiRhel1Install.png").similar(0.85).targetOffset(0,17), 8)
    click(Pattern("CiwiRhel1Install.png").similar(0.85).targetOffset(0,17))

def enterRemoteLoginCredentials():
    wait("CiwiRemoteLoginCredentials.png", 8)
    click("CiwiRemoteLoginCredentials.png")
    
    # Use keyboard to enter username, password, and to clear the default
    # text for the "Install as User" field using the \b
    type("\t\tautotest\tautotest\t\b\t\t\n")

def enterInstallPath(path):
    wait("CiwiInstallationPathDialog.png", 8)
    click("CiwiInstallationPathDialog.png")
    type("\t\t" + path + "\t\t\t\n")

def enterSiteSpecificQuestions():
    wait("CiwiSiteSpecificQuestions.png", 8)
    click("CiwiSiteSpecificQuestions.png")
    type("\t\t\t\n")
    
def enterPhysicalHosts():
    wait("CiwiHosts.png", 8)
    click("CiwiNext.png")

def submitTransaction():
    wait("CiwiSummaryDialog.png",8)

    # Tab to Install or Unstall button
    type("\t\n")

    if (exists("CiwiTransactionSubmittedDialog.png", 8)):
        click("CiwiTransactionOKButton.png")
    else:
        click("CiwiTransactionOKButton.png")
        print "Ciwi.submitTransaction Error:  Transaction failed\n"
        exit (1)

def startUninstall():
    click(Pattern("CiwiRhel1Uninstall.png").targetOffset(44,15))

def doLogout():
    click("CiwiLogout.png")
    
    
        
        
        
    

    
    
    
    
    
     
   
    
    
    
    
    
    
    
    