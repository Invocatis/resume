
[Formatted Printable Version](https://rawcdn.githack.com/Invocatis/resume/95d2d9587cfa01cc72e2b77199bb02ad1ad9250e/resume.html)

# Luke Cook

---

### Computer Science (BS) - 2017

**Rochester Institute of Technology**

- **Email:** invocatis@gmail.com
- **Location:** New York, United States
- **LinkedIn:** [linkedin.com/in/invocatis](https://www.linkedin.com/in/invocatis)
- **GitHub:** [github.com/invocatis](https://www.github.com/invocatis)

---

## Experience

### Senior Software Engineer (2022-2023)
### Software Engineer (2020-2022)
- **Datasembly**
- **Location:** Remote
- **Duration:** March 2020 - July 2024

#### Responsibilities
- Build & maintain web scrapers for grocery domain data
- Reverse engineer site APIs; adapt to scraping framework
- React to bot detection; develop and apply obfuscation techniques
- Work closely with Product and Customer team to convert website data to actionable insights for customers

#### Project
- Developed headless browsing framework in Scala using Playwright
  - Proposed, by me, to replace the existing framework written in Puppeteer / Javascript
  - Playwright on the JVM greatly reduced development and deployment complexity; no longer needed to maintain a cluster of headless workers, or a remote execution DSL
  - Enabled scraping of websites that required Javascript execution, had sufficiently complex login APIs, or employed encryption of API request information
  - Framework was developed to easily allow different headless browser libraries to be used

#### Skills
- Scala
- Scala Cats
- Puppeteer / Playwright
- SQL
- GCP
- HTTP Protocol
- Airflow
- Web Scraping

---

### Software Engineer Contractor
- **Hyperfiddle**
- **Location:** Remote
- **Duration:** March 2020 - September 2020

#### Responsibilities
- Development on Full Stack Clojure(script), Datomic CRUD application framework
- Setup CI/CD pipeline using CircleCI and AWS ECR and ECS, configured in Terraform

#### Projects
- Formalized an extensible algebra for Datomic statements
- Research and Development:
  - Reactive streams for rendering in Clojurescript
  - More ergonomic, and idiomatic category theory based programming in Clojure
  - Pattern Matching algorithms

#### Skills
- Clojure(script)
- Datomic
- Reactive Streams
- Haxe
- AWS ECS
- Docker

---

### Application Programmer II
- **Cornell University**
- **Location:** Ithaca, New York
- **Duration:** June 2019 - June 2020

#### Responsibilities
- Worked on backend of genomic data ETL application
- Created CSV file digest framework
  - Provided a data file, and a descriptive aspect file, the application produces a stream tuples that would be committed to a database.
  - Aspects would be used to generate streams of cells; these streams would be aligned based on properties of each aspect
  - Written in Java; strong focus on an object-oriented design
- Developed framework for automated end-to-end testing; focused on composability, ease of use, and self-cleanup
  - Developed domain-specific scripting language to simplify semantic layer of test suit in 3 layers: Arrange/Act, Assert, and Cleanup
  - Was built in 3 separate components: A language backend (written in Clojure), a business logic middle layer (written in Java), and a testing logic script suite (written in custom syntax)
  - Cleanup ensured automatic test independence and idempotency; tests were often run on deployed production environments

#### Skills
- Java
- Clojure
- Docker
- PostgreSQL
- Object-Oriented Design

---

## Projects

### eldritch
_Algebraic Data Types & Pattern Matching_
[github.com/invocatis/eldritch](https://www.github.com/invocatis/eldritch)

### motif
_Recursive Pattern Matching in Clojure_
[github.com/invocatis/motif](https://www.github.com/invocatis/motif)

### ernie
_Testing Framework for Java, written in Clojure_
[github.com/invocatis/ernie](https://www.github.com/invocatis/ernie)

### Scala 3 Algebraic Type Exploration
_An exploration of algebraic concepts._
_Defined in Scala 3's type system_
[github.com/invocatis/scala3-algebraic-type-exploration](https://github.com/Invocatis/scala3-algebraic-type-exploration)
