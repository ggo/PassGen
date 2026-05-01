graph LR
    subgraph "Entorno del Arquitecto"
        A[Editor: VS Code / Obsidian] -->|Escribe MD + Mermaid| B(Local Git Repo)
    end

    subgraph "GitHub (Source of Truth)"
        B -->|git push| C{Repositorio GitHub}
        C -->|Trigger| D[GitHub Action]
    end

    subgraph "Proceso de Transformación"
        D --> E[Autenticación API Atlassian]
        D --> F[Renderizado de Diagramas]
        D --> G[Conversión MD a XHTML]
    end

    subgraph "Confluence (Presentación)"
        G --> H((Página de Arquitectura))
        F --> H
    end

    style C fill:#f9f,stroke:#333,stroke-width:2px
    style H fill:#0052CC,color:#fff,stroke-width:2px
