# Spring Restaurant
A RESTful API designed to serve as a backend for a restaurant's website. This API includes authentication and management of a restaurant's employees and manager.

## Description
This API is meant to serve data relating to a restaurant's menu, hours, and contact info. Support is provided for a manager to update contact info, hire, and fire employees. Employee accounts that are created will receive an email with their username and password. Employees have the ability to manage the menu and store hours. Menu items that are uploaded to the server contain a name, image, price, and availability. This app will need access to a mysql database (see [here](https://github.com/JohnCornwell/SpringRestaurant/blob/main/src/main/spring_restaurant.sql) for a setup file). This app will also need access and permission to write to the folder C:/public/images where image uploads are stored and served.

## Author and Contributors
John Cornwell

This project is based off of a website called "FrontDash" that was developed by John Cornwell and Logan Larson for Dr. Kasi Periyasamy. This set of sites was meant to emulate the functionality provided by DoorDash and other such sites.
