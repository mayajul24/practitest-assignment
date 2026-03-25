# practitest-assignment

A ClojureScript SPA built with [re-frame](https://github.com/day8/re-frame) that fetches and displays posts from the [JSONPlaceholder API](https://jsonplaceholder.typicode.com/posts).

## Features

- Fetches 100 posts from the JSONPlaceholder API
- Loading and error states
- Collapse/expand each post body on click
- Search by title (case-insensitive, real-time)
- Pagination — 10 posts per page

## Prerequisites

- [Node.js](https://nodejs.org/) (v16 or later)
- [JDK 11 or later](https://openjdk.org/)

## Running the App

1. Install dependencies:
   ```sh
   npm install
   ```

2. Start the development server:
   ```sh
   npx shadow-cljs watch app
   ```

3. Wait for `[:app] Build completed` to appear in the output, then open [http://localhost:8280](http://localhost:8280) in your browser.

> The first build may take up to 30 seconds.

Changes to ClojureScript source files are hot-reloaded automatically.

## Tech Stack

- [ClojureScript](https://clojurescript.org/)
- [re-frame](https://github.com/day8/re-frame) — state management
- [Reagent](https://github.com/reagent-project/reagent) — React wrapper
- [shadow-cljs](https://github.com/thheller/shadow-cljs) — build tooling
