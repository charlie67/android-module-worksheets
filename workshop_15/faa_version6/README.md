# README

Code for the CS31620 FAA (Feline Adoption Agency) app, version 6.

This is version 6 developed by students in workshop 12, workbook 12.

We add support for incoming Intent requests to display the CatsFragment 
tab for the FAAMainActivity. See the AndroidManifest.xml file for the 
Intent Filter's Action and Data URI. We didn't really need a Data URI
in this example, but it was included for reference. The Data URI is more
useful where you have ContentProvider shared databases that you wish to provide
access to or where URIs map to web browsing features.

This version also (when ready) provides an activity to allow the user to add
a new cat to the FAA. This requires the firing up of the camera app to capture
an image of the cat.