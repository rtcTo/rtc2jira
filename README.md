# What is this?

This tiny program lets you migrate work items from an ibm rtc server to another issue tracker like Jira or Bitbucket.

# How can I use it?

Currently you can't... I just started developing it.

## Preconditions

If you still want to help me or just preview my work you need following preconditions:

*  Download the **[RTC Plain Java Client Libraries](https://jazz.net/downloads/rational-team-concert/releases/5.0.1?p=allDownloads)** from IBM and extract it to a folder of your choice. To avoid an account creation on jazz.net site, you could use: (http://bugmenot.com/)
*  Clone this project in Eclipse
*  Add a User Library named RTCClientPlainJavaLibs and add all .jars from the folder where you extracted the  Plain Java Client Libraries
*  Run maven update in your Eclipse
*  Copy settings.properties.example from src/main/resources to the root of your project and adjust the properties to your needs.

## Run it

Run the Main class. Watch your console and have fun fiddling around. 

# Basic conditions

RTC2Jira runs with IBM RTC Server Version 5.0+ (We use 5.0.1)