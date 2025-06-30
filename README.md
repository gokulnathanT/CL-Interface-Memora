
# 🧠 Memora CLI
Memora is a powerful **Command Line Interface (CLI)** personal productivity assistant that helps you manage your **tasks**, **notes**, **meetings**, and **bookmarks**, along with the ability to generate **weekly productivity reports** — all from your terminal.


## Features

- ✅ Task Manager – Add, view, and update your daily tasks
- 🗒️ Notes Keeper – Capture ideas, thoughts, and technical notes
- 📅 Meeting Scheduler – Fix, view, and cancel your upcoming meetings
- 🔖 Bookmark Saver – Save useful links and access them easily
- 📊 Weekly Report Generator – Compile weekly activity summaries in Markdown
- 🔗 Obsidian Integration – Open generated reports in Obsidian automatically



## Installation

**Prerequisites**
- Java 17+ or GraalVM 21+
- Maven
- Internet connection (for Maven dependency fetching)

**Build**
 

 ```bash
 mvn clean install
```

**Run**
 ```bash
 java -cp "target/classes;target/dependency/*" org.example.CLIProject memora
```

## CLI Execution


**Task Manager**
```bash
memora load task --addTask "Review pull requests"
memora load task --Pending
memora load task --Completed
memora load task --Update 3

```
**Notes Keeper**
```bash
memora load notes add --title "Meeting Recap" --content "Discussed Q3 goals and deadlines"
memora load notes --allNotes
memora load notes --getNote 1

```
**Meeting Scheduler**
```bash
memora load meeting fixMeet --date 2025-06-30 --context "Obsidian Workflow Session"
memora load meeting --getMeets
memora load meeting --cancel 2

```
**Bookmarks**
```bash
memora load bookmark add --title "Java Docs" --url "https://docs.oracle.com/en/java/"
memora load bookmark --getUrls

```
**Weekly Report**
```bash
memora load report --WeekSummary "Report_2025_Week27"
```


## 🙋‍♂️ Connect with Me

Made with 🤍 by [Gokulnathan Thanapal](https://www.linkedin.com/in/gokulnathan-thanapal-815586259/)
