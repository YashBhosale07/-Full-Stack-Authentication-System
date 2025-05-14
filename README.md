This project is a complete Full-Stack Authentication System designed to handle user registration, login, and access control using modern web technologies. The goal is to provide a secure, scalable, and easy-to-understand system that demonstrates how frontend and backend applications can work together to manage user sessions securely.

On the backend, it uses Spring Boot along with Spring Security and JWT (JSON Web Token) to handle authentication and authorization. User credentials are validated and securely stored in the database, and upon successful login, a JWT is issued which the frontend can use to access protected resources.

On the frontend, the application (e.g., built with React or another modern JavaScript framework) communicates with the backend APIs. It stores the JWT token securely (usually in localStorage) and sends it with each request to access user-specific or protected data.

This project mimics real-world authentication systems used in production environments, making it ideal for learning and understanding secure login workflows, token-based authentication, and role-based access control.
