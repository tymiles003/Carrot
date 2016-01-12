![Carrot Logo](http://temp.boxedfolder.com/smartlogo_carrot.png =476x)

# What is Carrot?

Carrot is the beacon management system for everyone. It's build on top of the popular Spring framework und utilizes AngularJS as a frontend component.

The following technologies are in use:

+ Spring Boot
+ Spring Security
+ Spring Data / Hibernate
+ HSQLDB
+ Thymeleaf
+ Joda Time
+ Bootstrap
+ jQuery

# How to get started?

This repository consists of two separate projects. The folder 'core' contains the Spring Boot project and the folder 'client' includes the AngularJS client application. Dependencies are managed via Maven & Bower. You can run both _client_ and _core_ on your local machine, your own server hardware or deploy everything to the cloud (e.g. Heroku via Spring Cloud). Use the appropriate _application.yml_ file to configure your datasource.

_See the CarrotSDK repository for additional details on how to communicate with devices._

## Development

Run Spring Boot by running the command ´mvn spring-boot:run´ (_/core_). When your Spring Boot application is running, you can fire up the build-in frontend server by running ´grunt server´ (_/client_).

## Deployment

To integrate the client module into the core, run the grunt command ´grunt build´. All assets, html, js and css files are being copied to the resources directory inside your Spring boot project.

# Example

An instance of carrot is running on Heroku under http://carrotbms.heroku.com. Login with _admin_ (username) and _carrot_ (password).

# What's next?

The following features are still missing:

+ __(Advanced) user management__ - Currently we have a single user with credentials stored inside the application settings file. This should be replaced with a proper user and role management in the future.

+ __Support for binary files__ - Carrot's current support for text and notifications should be extended to allow file attachments. The syncing process needs to be overhauled for that purpose.

# License

Carrot is released under version 3 of the [GPL](http://www.gnu.org/licenses/gpl-3.0.en.html) or any later version.
