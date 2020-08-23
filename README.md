# WeightTracker Mobile App

## UI
[WeightTracker Mobile App UI Design](https://github.com/paronicholas/WeightTracker/blob/master/Nicholas_Paro_Weight_Tracker.zip)

## Requirements
* Develop an Android application which utilized three tables within a database - one for login information, one for target weight information, and one for daily wieght information.
* The app needs to contain a login page, a logbook (which contains a GridView where the daily weights are displayed for the user), and a page where the user can add a target weight and enable Push Notifications to notify the user when they have achieved or surpassed their target weight goal.
* The app needs to be able to save and verify the user's login information, display all of the user's entered daily weight information in a Gridview, and the ability to turn off and on push notifications.

These requirements are the basis for the weight management application. In addition to these basic requirements, is the implied requirement to allow a user to add in a new daily weight to be added to the database, stored according to their username, and retrieved for the logbook.

## UX/UI
This application needed to posses the following screens/features:
* Login screen to verify the current user and get the weights logged for that account
* A logbook screen to show the logged daily weights in a GridView
* The ability to log a new weight. In this design, there is a page dedicated to adding a new daily weight
* A profile page which contained the ability to turn on and off push notifications for when the user met or exceeded their target goal and the ability to log their target weight

The design I created kept the user in mind by creating a simple and standardized format throughout the entire application. All of the pages had the same color scheme, font choices, overall spacing, and once logged in there was a bottom navigation bar which was present on all of the pages. This created continuity between all of the pages and allowed the user to feel that they were in a unified and complete experience.

## Coding Process
My coding process was simple and followed the same practice I use on a day-to-day basis - start from the broad strokes of the design and build into the more granular components. This allowed me to first design out the UI, then code out the ability to move between each screen of the application. This process involved linking through buttons and moving between pages using the bottom navigation bar. Once this was complete and a user to freely move between pages, I began to build in the functionality of the database. I started this by first creating the code to create the tables, then to store data within the tables, and then to retrieve the data from the table.

## Testing
For the testing, I relied on two main features within Android studio - logging to view various code outputs, ensure stateful variables, and verify data was being successfully written and retrieved from the database. The use of logs and output value allowed me to ensure the data being passed within this application was completed successfully. The second feature I used within Android studio was the emulator. This allowed me to ensure that I was able to test the functionality of the various buttons and components on the screen. This helps to ensure the user is able to access the application successfully and use all of the intended features.

## Area of Success
I think a major area of success in this application was the overall structure of the application and the code organization. I feel that one of my strengths in the clarity of my Class, method, and naming conventions. One of my main goals is to allow another developer to understand the goals and use of a Class and its methods without the need for additional comments. I think comments have their place and can be essential to understanding challenging pieces of code; however, the can often times be overused and abused to justify messy and unclear code.
