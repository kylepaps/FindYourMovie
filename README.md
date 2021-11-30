# FindYourMovie - The one-stop-shop for picking your flick on movie night!

## Getting Started

This Android application utilizes both Firebase Realtime Database and SQLite. 
Firebase is a BaaS, hence the app will not run unless Firebase is configured with the project.
This [Firebase Setup Guide](https://www.youtube.com/watch?v=dRYnm_k3w1w&t=1s) will get you started :)

Once Firebase is configured with the project you must initialize the Realtime Database. 
The database is formatted as follows:

![image](https://user-images.githubusercontent.com/64933273/144143860-808c296d-9ad1-4f70-a78a-36de2e140a10.png)

Now the app is configured and all that's left is the start it!

## App Demo

Once the application is started you will be taken to the LandingPageActivity.

![findyourmovielandingpage](https://user-images.githubusercontent.com/64933273/144142357-cc0ae7af-387f-466a-8138-46d17cbe95b0.png)

Pressing the "Get Started!" button on the LandingPageActivity will bring you to the MainActivity.

![popularmoviespage](https://user-images.githubusercontent.com/64933273/144144257-c08b87dd-0820-4004-a6d0-33c2a104693c.png)

From the MainActivity page you can click into one of the popular movies or interact with the toolbar.


![app-toolbar](https://user-images.githubusercontent.com/64933273/144144522-d4c76613-33a0-48ff-800f-74513ad44b2a.png)

The toolbar features 4 icons, each function as follows:

  1. Access the WatchlistActivity. This is a list of movies saved to the SQLite database unique to each Android device. Movies can be deleted  within the Activity.
  2. Access the AboutActivity.
  3. Access the full list of movies saved to the Firebase Realtime Database. Movies are clickable and lead to a fragment displaying movie details and streaming platforms.
  4. Search movies in the Realtime Database.
 
All movies selected from each movie list or search will lead to the MovieFragment.

![moviefragment](https://user-images.githubusercontent.com/64933273/144145350-4f1c68c0-f482-4cd7-a062-a2b57cc0b7f0.png)

From here movies can be added to the Watchlist.




