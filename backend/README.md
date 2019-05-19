# Backend

The backend fetches the E-Mail from the G-Mail Account and provides a simple rest-api for the frontend.


## Development

If you don't want to fetch emails during development, activate the spring profile 'staticImageCache'.
This will provide a few static images in random order.

## Production

To fetch emails from your G-Mail account, you first need to create an [app-password](https://support.google.com/accounts/answer/185833)
Then create a file named 'mail.properties' in resources folder.
Provide the following properties:
* email = yourEmail@gmail.com
* password = theGeneratedAppPassword
* folder = folder to fetch emails from, e.g. 'inbox' or '[Gmail]/Drafts' (names are dependent on your location. In e.g. germany you have to use '[Gmail]/Entwürfe')

