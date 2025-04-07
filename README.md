# CS321-Group-7

### 📄 Project Description

**Functional Area 7: Petition for Alien Fiancé(e) and Children**  
This system allows an immigrant to request entry for their Alien Fiancé(e) and Children after verifying they have not already been granted access under a different immigrant. The system supports data entry, review, and approval workflow steps as part of an immigration processing system developed under Agile methodology.

---

### 🗂️ Repository Structure (Iteration 3 Requirement)

This repository is organized to follow a clean Maven-based Java project structure, which supports modularity, testing, and future automation (CI/CD).

- `src`
  - `main`
    - `java`
      - `edu`
        - `gmu`
          - `cs321`
            - `App.java`
            - `Dependent.java`
            - `Immigrant.java`
            - `Petition.java`
            - `Screen.java`
            - `Status.java`
  - `test`
    - `java`
      - `edu`
        - `gmu`
          - `cs321`
            - `DependentTest.java`
            - `ImmigrantTest.java`
            - `PetitionTest.java`
            - `ScreenTest.java`
            - `StatusTest.java`
- `.vscode/` – VSCode project settings
- `.gitignore` – Files/folders to ignore in Git
- `.gitattributes` – Normalizes line endings and encoding
- `README.md` – This file
- `pom.xml` – Maven configuration file
  
#### ✅ Key Files:
- `Dependent.java`: Contains the domain logic for dependent data and inherits method stubs for create, update, and get operations from Immigrant.
- `Immigrant.java`: Contains the domain logic for immigrant data and method stubs for create, update, and get operations.
- `Petition.java`: Contains the domain logic for petition data and method stubs for create, update, and get operations.
- `Screen.java`: Contains the domain logic for the screen and method stubs for display.
- `Status.java`: Contains the domain logic for Status data and method stubs for update, get, and send operations.
- `DependentTest.java`: Contains JUnit tests that follow the TDD approach.
- `ImmigrantTest.java`: Contains JUnit tests that follow the TDD approach.
- `PetitionTest.java`: Contains JUnit tests that follow the TDD approach.
- `ScreenTest.java`: Contains JUnit tests that follow the TDD approach.
- `StatusTest.java`: Contains JUnit tests that follow the TDD approach.
- `.gitignore`, `.gitattributes`: Project hygiene and version control rules.
- `README.md`: Project and repository documentation.
- `pom.xml`: Maven configuration file (build automation, dependencies, etc.)

---

### 👨‍💻 Contributors
- Nathan Albe 
- Isaiah Lewis
- Christopher Vargas

---
