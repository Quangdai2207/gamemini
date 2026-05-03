Clone the app Gamemini on Git hub by command:
git clone git@github.com:Quangdai2207/gamemini_springboot.git       => clone by ssh
git clone https://github.com/Quangdai2207/gamemini_springboot.git   => clone by http
Download file zip directly.

After cloned gamemini, at the current directory, move to gamemini project to run application by Docker.
Follow these steps to run project by Docker.
When you're ready, start your application by running:
`docker compose up --build`.    => start app
Press `Ctrl + C`                => to stop application
Your application will be available at http://localhost:8080.

If you wanna run application as background, use command
`docker compose up --build -d`. => start as background
`docker compose down`           => stop application.
After keystroke this command, application will run at http://localhost:8080 as background

While docker build container for app, if error occurred, use command to remove old data and rebuild
`docker compose down -v`        => remove all old data
`docker compost up --build`     => rebuild container






