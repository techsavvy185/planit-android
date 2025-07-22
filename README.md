#  PlanIt - The Intelligent Daily Planner
<p align="center">
<img width="250" height="250" alt="PlanIt Logo" src="https://github.com/user-attachments/assets/8b0d2c15-67ad-4f56-a459-d0c51865689c" />
</p>
**PlanIt** is a modern, feature-rich daily planner application for Android, designed to be your all-in-one companion for organizing your life. Built entirely with Kotlin and the latest Jetpack libraries, this project serves as a showcase of modern Android development architecture and best practices.

The app provides a clean, intuitive, and dynamic user interface built with Jetpack Compose, focusing on a seamless user experience.

---

##  Features

*  **Smart Home Screen:** A dynamic dashboard that greets you based on the time of day, displays an inspirational quote fetched from a live API, and provides an at-a-glance summary of your daily schedule.

*  **Weekly Calendar View:** An intuitive, horizontal calendar to view and manage the current week's activities.

*  **Interactive To-Do List:** A fully functional to-do list where you can add, edit, and check off tasks for any selected day. All changes are saved and updated in real-time.

*  **Transaction Management:** Easily log your daily expenses and income to keep track of your finances.

*  **Event Scheduling:** Create and view events with dedicated, modern date and time pickers.

*  **Modern UI:** A clean, modern top bar and a user interface built with Material 3 components making use of the dynamic colors in Android and a custom variable font(Inter) for a premium feel.

---

##  Upcoming Features

Here's a look at what's planned for the future of PlanIt:

* **Microsoft Teams Integration:** Automatically pull and display your upcoming meetings from your Teams calendar.

* **Google Calendar Sync:** A two-way sync to keep your planIt events and Google Calendar perfectly aligned.

* **Web Client:** A web-based version of planIt for multi-platform access to your schedule and tasks.

---

##  Screenshots
<img width="234.67" height="495.67" alt="Screen2" src="https://github.com/user-attachments/assets/e03af5a2-ba54-412c-a732-6648d54e3ff9" />
<img width="234.67" height="495.67"  alt="Screen3" src="https://github.com/user-attachments/assets/495d2186-d61a-42bf-8830-3e10375e49eb" />
<img width="234.67" height="495.67"  alt="Screen4" src="https://github.com/user-attachments/assets/0b002603-a974-4652-866e-2b0933f5bb13" />
<img width="234.67" height="495.67" alt="Screen1" src="https://github.com/user-attachments/assets/8a6911fa-b716-4276-8a0b-393534f8390a" />
<img width="234.67" height="495.67"  alt="Screen5" src="https://github.com/user-attachments/assets/3f184564-a16c-481f-ac6d-a0dd0f965d2e" />
<img width="234.67" height="495.67" alt="Screen6" src="https://github.com/user-attachments/assets/10f0964a-da51-44a7-8a55-ad94ffaf9209" />


##  Tech Stack & Architecture

This project is built with a modern, scalable, and maintainable architecture.

* **UI:** 100% Jetpack Compose for a declarative and modern UI.

* **Architecture:** MVVM (Model-View-ViewModel) with a Repository Pattern.

* **Dependency Injection:** Dagger Hilt for managing dependencies and decoupling components.

* **Database:** Room DB for robust, local persistence of all user data.

* **Networking:** Retrofit & Gson for consuming the ZenQuotes REST API.

* **Asynchronous Programming:** Kotlin Coroutines and Flow for efficient, non-blocking data handling and reactive UI updates.

* **Navigation:** Jetpack Navigation Compose for managing screen transitions and the back stack.

---
##  Author
[@techsavvy185](https://github.com/techsavvy185)


##  Getting Started

To build and run this project locally, you will need:

1. Android Studio (latest stable version recommended)

2. An Android device or emulator running API level 31 or higher.

Simply clone the repository and open it in Android Studio. The project uses Gradle to handle all dependencies automatically.



```bash
git clone https://github.com/techsavvy185/planit-android-app.git
