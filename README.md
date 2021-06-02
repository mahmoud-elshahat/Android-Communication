# Android Communication
Three android apps (Emitter - Middle Man - Receiver ) which run on same device communicating using broadcast receivers and socket programming.

## The Idea
- Emitter app get dummy users data from calling api and show them in list, When user click on anytime it sends this user data to middle app using broadcast receiver.
- Middle app recieve it and send it to receiver using client server methedology and middle will be server in this case.
- Receiver app receive user and add it to database and show all users in list and sent response to middle about saving data status.
- Middle retun this status to emitter telling it that sending user completed successfully or there is wrong.


### Demo Video

<img src="demo.gif" height="400em" width="200em">
