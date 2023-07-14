```
└── design-hub-application
├── adapters
│   ├── inbound
│   │   └── [REST Adapters - Exposed to external systems]
│   │       ├── RestController.java
│   │       └── ...
│   ├── outbound
│   │   └── [Persistence Adapters - Implementations of repositories]
│   │       ├── JpaUserRepository.java
│   │       └── ...
│   └── security
│       └── [Security Adapters - Implementations of authentication/authorization]
│           ├── JwtTokenProvider.java
│           └── ...
├── application
│   └── [Application Services - Business logic]
│       ├── UserService.java
│       └── ...
├── converters
│   └── [Converters - Conversion logic between entities and DTOs]
│       ├── UserConverter.java
│       └── ...
├── domain
│   ├── model
│   │   └── [Domain Model Objects - Core business entities]
│   │       ├── User.java
│   │       └── ...
│   └── ports
│       └── [Ports - Interfaces defining use cases and boundaries]
│           ├── UserServicePort.java
│           └── ...
└── config
└── [Configuration - Spring configuration classes]
├── SecurityConfig.java
└── ...
```

