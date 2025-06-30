# 🧠 Memora Backend – Spring Boot REST API

The **Memora Backend** is a lightweight, modular RESTful API service built using Spring Boot. It supports a productivity-focused CLI frontend by handling operations like:

- ✅ Task Management  
- 🗒️ Notes Keeper  
- 📅 Meeting Scheduling  
- 🔖 Bookmarking  

Each module exposes a set of clean, JSON-based endpoints for CRUD-like operations and activity summaries.

---

## 🚀 Features

- Add, update, and view daily tasks
- Create and retrieve notes
- Schedule and cancel meetings
- Store and manage helpful bookmarks
- Fetch weekly activity data for reports

---

## 📦 Tech Stack

- Java 21 / GraalVM Compatible  
- Spring Boot 3+  
- RESTful APIs (JSON)  
- Maven

---

## 👽 API Reference

### ✅ Tasks API – `/api/task`

| Method | Endpoint          | Description                            |
|--------|-------------------|----------------------------------------|
| POST   | `/addTask`        | Add a new task                         |
| GET    | `/getPending`     | Fetch pending (incomplete) tasks       |
| GET    | `/getCompleted`   | Fetch completed tasks                  |
| POST   | `/updateTask`     | Mark a task as completed (pass `id`)   |
| GET    | `/Activity`       | Get tasks from the past 7 days         |

**Sample Payload – Add Task**
```json
{
  "content": "Review pull requests",
  "status": "PENDING"
}
```
### ✅ Notes API – `/api/note`

| Method | Endpoint          | Description                            |
|--------|-------------------|----------------------------------------|
| POST   | `/addNote`        | Add a new note                         |
| GET    | `/getNote/{id}`     | Get a note by ID        |
| GET    | `/getAllNotes`   | Fetch all notes                  |
| GET   | `/Activity`     | Get notes from the past 7 days   |

**Sample Payload – Add Note**
```json
{
  "title": "Meeting Recap",
  "content": "Discussed Q3 roadmap and deliverables"
}
```

### ✅ Meeting API – `/api/meet`

| Method | Endpoint          | Description                            |
|--------|-------------------|----------------------------------------|
| POST   | `/addMeeting`        | Schedule a new meeting                        |
| GET    | `/getAllMeets`     | Fetch all scheduled meetings        |
| GET    | `/getTodayMeetings`   | Fetch meetings scheduled for today                  |
| POST   | `/updateMeet`     | Cancel a meeting (pass id)   |

**Sample Payload – Add Meet**
```json
{
  "context": "Product Sync-Up",
  "date": "2025-06-30"
}
```


### ✅ Bookmark API – `/api/bookmark`

| Method | Endpoint          | Description                            |
|--------|-------------------|----------------------------------------|
| POST   | `/addUrl`        | Add a new bookmark               |
| GET    | `/getUrls`     | Fetch all saved bookmarks      |
| POST    | `/deleteUrl`   | Delete a bookmark by ID                  |
| GET   | `/Activity`     | Get bookmarks from the past 7 days   |

**Sample Payload – Add Bookmark**
```json
{
  "title": "Java Docs",
  "url": "https://docs.oracle.com/en/java/"
}

```



**Backend**
-https://github.com/gokulnathanT/CLI-Memora-Backend

## 🙋‍♂️ Connect with Me

Made with 🤍 by [Gokulnathan Thanapal](https://www.linkedin.com/in/gokulnathan-thanapal-815586259/)
