# README

Code for the CS31620 FAA (Feline Adoption Agency) app, version 7.

This is version 7 developed by students in workshop 13, workbook 13.

This version adds some JUnit and Espresso tests. It also adds build variants 
where one variant uses an in-memory database and another a persistent 
database. The appropriate database is injected depending on the build
variant selected.

Some attempt has been made in the addcat package to follow the
Model View Presenter design pattern. The workbook discusses why
this was done making reference to the Codelabs tutorial: 
https://codelabs.developers.google.com/codelabs/android-testing/index.html#0

I have not taken advantage of MVP for test mocking but encourage students
to see whether they can apply some of the ideas in the tutorial
to this version of the FAA app. 

If you build with the mockDebug variant and run the project, then an 
in-memory database is used and you won't see any data.

You will only see a populated database with the prodDebug variant.

If you get an installation error when running the app then try and
uninstall the old version on the emulator/device. This happens when
you switch between variants.