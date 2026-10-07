# AGENT_USAGE

## AI Assistance Used

ChatGPT was used as a development assistant during the implementation of the Expense Reviewer application.

## Representative Prompts

Examples of prompts used:

- Help build a Spring Boot expense claim management application.
- Help create REST APIs for expense claims.
- Help debug MySQL and Spring Boot configuration issues.
- Help add validation and policy validation.
- Help add pagination and sorting.
- Help integrate a Gemini API for AI-based expense review.
- Help test APIs and fix runtime errors.
- Help create frontend HTML, CSS, and JavaScript for the application.

## Delegated Work

AI assistance was used for:

- Project structure suggestions
- Java and Spring Boot code assistance
- REST API implementation
- Frontend HTML/CSS/JavaScript assistance
- Gemini API integration
- Debugging errors
- Test case suggestions
- README and documentation preparation

## Important AI Decisions

The AI was designed as a recommendation system rather than an automatic approval system.

The workflow is:

User submits claim → AI reviews claim → AI recommends APPROVE/REJECT/REVIEW → Human reviewer makes the final decision.

This ensures that important financial decisions remain under human control.

## AI Mistakes and Verification

Some suggested Gemini model names were unavailable or temporarily overloaded. These suggestions were tested against the actual API response and corrected.

The Gemini integration was verified using the application API and returned a successful recommendation.

The application was also verified using:

- Maven tests
- REST API testing
- Frontend testing
- MySQL persistence testing
- Swagger API testing

## Verification

AI-generated code was reviewed, executed, and tested locally before being included in the project.

Final approval and rejection actions are performed by the human reviewer, not automatically by the AI.
