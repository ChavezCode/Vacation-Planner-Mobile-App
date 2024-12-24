#Title: Vacation Planner Mobile App
____________________________________________________________________________________
The purpose of this mobile application is to help make vacation planning easier to track and manage. The Vacation Planner app allows users to enter, edit, and delete as many vacations as possible and excursions for each if necessary. The app will enable users to set alerts and share details via SMS, e-mail, or clip vacation details. 


##BASIC INTRUCTIONS:
________________________________________________________________________
The project must be built and run using Android Studio with an emulator or phone with a minimum SDK/API level of 21.  
The following steps go through how to work the app:

1. Click on the "Let's Start Planning!" button in the main activity. 
2. Click the add button in the bottom right corner to add a vacation. 
- 	a. The button routes the user to a different page named Vacation Details.
3. There will be two fields to fill out on the Vacation Details page. The first field takes the name of the vacation, and the second field takes the name of the hotel or other place of stay.
4. Click on the "Start Date" button and select a start date for the vacation. 
5. Click on the "End Date" button and select an end date. The end date will need to be after your start date. Otherwise, it will not save, and an alert will pop up. 
6. Go to the menu in the vacation details page, by clicking on the top right three dots, and select “Save.”
a. Clicking "Save" reroutes the user to the "My Vacations" page. Notice how vacation is now added and visible. Vacations listed appear in the order in which they were added, with the older vacations at the top and the newer ones at the bottom. 
7. Click on the vacation that you added to go back to the "Vacation Details" page.
8. Once on the "Vacation Details" Page, click on the "add" icon in the bottom right corner to go to the excursion details page and add an excursion.
9. On the "Excursions" page, In the field, type the name of the excursion.
10. Click the "Start Date" button and pick a date equal to or within the date ranges associated with the vacation. If a date before or after the vacation is selected, the excursion will not save, and an alert will pop up displaying the invalid date error. 
11. Go to the menu by clicking on the top right three dots and select Save to save the created excursion.
a. Once "Save" is clicked and there are no date issues, the app will reroute to the Vacation Details page. The excursion is now on the Vacation Details page. Any additional excursions will be listed in order in which they were added, with older excursions at the top of the list and newer ones at the bottom of the list.
12. Once on the "Vacation Details" page, click on the menu and click on "Set Reminder."
a. The reminder menu item will trigger start and end notifications once the date approaches. If the start and end dates are before the current date, both notifications will trigger with the start notification first, followed by the end notification. 
13. Go to the "Vacation Details" menu and select "Share details."
a. The sharing option will allow sharing vacation details via text or e-mail or saving them to a clipboard. The details include the name of the vacation, date ranges, and the location of the stay. 
14. On the "Vacation Details page, click on the excursion created.
a. Clicking on the excursion will take you to the excursions page. 
15. Change the title of the excursion to something different and adjust the date to be equal to or within the associated vacation date range. 
16. Go to the menu on the "Excursions" page and select "Set Reminder." 
a. The reminder menu option will trigger a notification if the current date of your excursion is on or before the current date.
17. Go to the menu on the "Excursions” page and select "Save."
a. The excursion will then be updated. 
18. To delete a vacation, remove any associated excursions by taking the following step; otherwise go to step 21 if there are no associated excursions:
19. Click on the excursion on the "Vacation Details" page.
20. On the "Excursions" page, go to the menu and select "Delete."
a. Clicking delete will route the user back to the vacation details page. Notice how the excursion is no longer on the list on the "Vacation Details" page. 
21. Go to the menu on the "Vacation Details" page and select "Delete." A notification will pop up informing that the vacation was deleted.

______________________________________________________________________________________________________________________

##APK DEPOLOYED AND SINED TO:
ANDROID STUDIO LADYBUG 2024.2.1 with a Targetsdk of 34.  

______________________________________________________________________________________________________________________

##PROJECT CAN BE FOUND HERE:

[git@gitlab.com:wgu-gitlab-environment/student-repos/jch1248/d308-mobile-application-development-android.git]
