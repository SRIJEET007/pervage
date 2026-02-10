
# 🛡️ Pervage

**Pervage** is a privacy-first, self-hosted photo storage platform built using **Spring Boot**.
Think of it as a **local, secure alternative to Google Photos** — without cloud surveillance, external alterations, or third-party control.

This project is designed especially for **non–tech-savvy users** who want strong privacy **without writing code or managing complex systems**.

---

##  Vision

Modern cloud photo apps trade convenience for privacy.

**Pervage flips that trade-off.**

*  No cloud uploads
*  No third-party access
*  Your photos stay **on your own desktop**
*  Accessible across devices on your local network

 **Pervage is ownership-first**.

---

##  What Problem Does Pervage Solve?

Technically, anyone *can* create a password-protected local photo vault.
But most people:
* Don’t know how to code
* Don’t want to manage servers
* Don’t want to deal with endless configurations
* Just want something that **works**
* 
**Pervage provides that simplicity.**

---

##  Core Idea

* A **Spring Boot backend** runs as a **local server** on the user’s desktop
* Users store their **private photos locally**
* The server can be accessed:

  * From the same desktop
  * From **any device on the same LAN network**
* Minimal configuration required
* No external cloud dependency

---


###  Privacy & Security
* Fully local storage
* Password-protected access
* No external API calls
* No telemetry, tracking, or analytics

###  Local Server
* Runs on the user’s desktop
* Configurable port & credentials
* Easy setup via config files

### Cross-Device Access
* Access photos via browser on:

  * Phone
  * Tablet
  * Laptop
* All within the same local network
---

##  Tech Stack

* **Backend:** Spring Boot (Java)
* **Storage:** Local file system
* **Authentication:** Application-level authentication
* **Networking:** Local LAN-based access
* **Future Client:** Lightweight mobile/web client

---

##  Setup (High Level)

1. Clone the repository
2. Configure a few values (port, password, storage directory).Create a .env file in root folder and include your details.
3. Run the Spring Boot application
4. Access the server from:

   ```
   http://<your-desktop-ip>:<port>
   ```

That’s it. No cloud accounts. No sign-ups.

---

##  Who Is This For?

* People who value **privacy over convenience**
* Users who don’t trust cloud photo providers
* Non-technical users who want a **plug-and-play** solution
* Developers interested in self-hosted systems

---

##  Roadmap

*  Photo upload & management UI
*  Album organization
*  Stronger encryption options
*  Lite mobile app for remote access

---
