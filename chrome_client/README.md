# Install App

Enable flags
Many of the Chrome Apps APIs are still experimental, so you should enable experimental APIs so that you can try them out:

Go to chrome://flags.
Find "Experimental Extension APIs", and click its "Enable" link.
Restart Chrome.
Load your app
To load your app, bring up the apps and extensions management page by clicking the settings icon  and choosing Tools > Extensions.

Make sure the Developer mode checkbox has been selected.

Click the Load unpacked extension button, navigate to your app's folder and click OK.

Open new tab and launch
Once you've loaded your app, open a New Tab page and click on your new app icon.

Or, load and launch from command line
These command line options to Chrome may help you iterate:

--load-and-launch-app=/path/to/app/ installs the unpacked application from the given path, and launches it. If the application is already running it is reloaded with the updated content.
--app-id=ajjhbohkjpincjgiieeomimlgnll launches an app already loaded into Chrome. It does not restart any previously running app, but it does launch the new app with any updated content.
