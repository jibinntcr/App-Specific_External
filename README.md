# Reference Implementation: App-Specific External Storage

**Course:** Software Development for Portable Devices (CS 10)  
**Institution:** BITS Pilani - Work Integrated Learning Programs (WILP)  

---

## 📖 Overview
This repository provides a practical demonstration of **App-Specific External Storage**. This storage model is the standard choice for modern Android applications that need to manage larger files—such as generated reports, activity logs, or media caches—that are still private to the application but require more space than the primary internal partition allows.

## 🎓 Instructional Objectives
The primary goal of this demo is to illustrate:
* **Storage Scalability:** Utilizing `getExternalFilesDir()` to access a spacious storage area dedicated to the app.
* **Zero-Permission Access:** Demonstrating that modern Android does not require system-wide storage permissions for files stored in these app-specific directories.
* **Data Persistence & Cleanup:** Understanding that while these files reside on external media, they are owned by the app and are purged upon uninstallation.

## 🏛️ Project Architecture
This implementation highlights the **"Decision Rule"** for professional Android development:
1. **Internal Storage:** Best for small, highly sensitive private files (notes, user settings).
2. **App-Specific External Storage:** Best for larger, private files (logs, CSV exports, generated PDFs).

### Implementation Highlights
* **File Exporting:** Uses `file.appendText()` to simulate real-world logging by adding data to the end of the file.
* **File Fetching:** Uses `file.readText()` to retrieve and display the complete record from the external path.
* **Path Transparency:** The application displays the absolute path in the status message to help students locate the files during debugging.

## 🛠️ Usage for Students
Students should explore the `MainActivity.kt` to observe:
1.  How to get the correct directory handle using `context.getExternalFilesDir(null)`.
2.  The standard Java `File` API operations used to manage content on external media.
3.  Why this method is preferred over hardcoded paths like `/sdcard/`, which are outdated and insecure.

---

**Topic:** Working with the File System  
**Previous Session:** Internal Storage (Private App Data)  
**Next Session:** CS 11 - Android Databases (SQLite)
