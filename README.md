# SBS Corp Agent

SBS Corp Agent is an enterprise Retrieval-Augmented Generation (RAG) system designed to help employees quickly access internal company information through a conversational AI assistant.

Instead of repeatedly contacting HR or other departments for routine questions, employees can ask the agent questions in natural language and receive answers grounded in the organization's available knowledge sources.

The system combines document and database ingestion, source-aware chunking, vector embeddings, semantic retrieval, metadata filtering, ranking/re-ranking, and an LLM-based response generation layer.

## 🎯 Problem

In an enterprise environment, employees often need to search for information related to company policies, procedures, internal documentation, or other business knowledge.

This information may be distributed across different sources, making it time-consuming to find the correct answer or requiring employees to contact the relevant department.

SBS Corp Agent aims to reduce this friction by providing a single conversational interface for accessing internal knowledge.

## 💡 Solution

The system follows a Retrieval-Augmented Generation architecture.

When an employee asks a question:

1. The question is received through a REST API.
2. Relevant knowledge is retrieved from the vector store using semantic similarity search.
3. Metadata filters and ranking strategies are applied to improve the relevance of the retrieved information.
4. The most relevant context is provided to the LLM.
5. The LLM generates an answer based on the retrieved context.
6. If the available context is insufficient, the system avoids hallucinating and responds that it does not have enough information to answer.

## 🏗️ Architecture

```text
                    ┌─────────────────────────┐
                    │      Data Sources        │
                    │                         │
                    │  • Evergreen Data       │
                    │  • Time-Sensitive Data  │
                    │  • PDFs                 │
                    │  • Databases            │
                    │  • Wiki / Documentation │
                    └────────────┬────────────┘
                                 │
                                 ▼
                    ┌─────────────────────────┐
                    │       Ingestion         │
                    │                         │
                    │  Source-specific        │
                    │  ingestion pipelines    │
                    └────────────┬────────────┘
                                 │
                                 ▼
                    ┌─────────────────────────┐
                    │        Chunking         │
                    │                         │
                    │  Source-aware chunking  │
                    └────────────┬────────────┘
                                 │
                                 ▼
                    ┌─────────────────────────┐
                    │       Embeddings        │
                    │                         │
                    │   Google Embeddings     │
                    └────────────┬────────────┘
                                 │
                                 ▼
                    ┌─────────────────────────┐
                    │      Vector Store       │
                    │                         │
                    │ Redis Vector Store      │
                    └────────────┬────────────┘
                                 │
                                 ▼
                    ┌─────────────────────────┐
                    │       Retrieval         │
                    │                         │
                    │ Semantic Similarity     │
                    │ Search — Top 10         │
                    └────────────┬────────────┘
                                 │
                                 ▼
                    ┌─────────────────────────┐
                    │   Ranking / Re-ranking  │
                    │                         │
                    │ Metadata-based ranking  │
                    └────────────┬────────────┘
                                 │
                                 ▼
                    ┌─────────────────────────┐
                    │          LLM            │
                    │                         │
                    │      Azure OpenAI       │
                    └────────────┬────────────┘
                                 │
                                 ▼
                    ┌─────────────────────────┐
                    │        Response         │
                    └─────────────────────────┘
```

## 🔄 RAG Pipeline

### 1. Data Sources

The system is designed to work with different categories of enterprise knowledge, including:

- Evergreen information — relatively stable knowledge that does not change frequently.
- Time-sensitive information — information whose relevance depends on dates or recent updates.
- PDF documents.
- Database records.
- Internal documentation / Wiki content.

Each source can have different characteristics and therefore requires an appropriate ingestion and chunking strategy.

### 2. Ingestion

The ingestion layer is responsible for extracting information from the different knowledge sources and transforming it into a common representation that can be processed by the RAG pipeline.

The system supports source-specific ingestion workflows rather than treating every source as a generic document.

### 3. Chunking

The extracted information is divided into smaller chunks before embedding.

The system uses source-aware chunking because different types of information have different structures.

For example, a PDF document and a database record should not necessarily be chunked using exactly the same strategy.

Metadata is also associated with chunks to preserve information about their origin and characteristics.

Examples include:

- Source
- Source type
- File name
- Database table
- Identity
- Chunk index
- Chunk size
- Chunk overlap
- Date / recency information

### 4. Embeddings

Each chunk is converted into a vector representation using Google's embedding models.

These vectors allow the system to compare the semantic meaning of the user's question with the available knowledge.

### 5. Vector Store

The generated embeddings and their associated metadata are stored in Redis using Redis Vector Store.

Redis is responsible for:

- Storing document embeddings.
- Performing vector similarity searches.
- Storing chunk metadata.
- Supporting metadata-based filtering.

### 6. Retrieval

When an employee asks a question, the system performs a semantic similarity search against the vector store.

The initial retrieval returns the top 10 most relevant chunks.

Metadata filtering can also be applied to narrow the search according to the characteristics of the stored knowledge.

### 7. Ranking / Re-ranking

Retrieval alone does not always guarantee that the most useful information appears first.

The system therefore applies an additional ranking layer after retrieval.

The ranking strategy can take into account metadata such as:

- Source priority.
- Database table priority.
- Recency of information.

This allows the system to prioritize information according to business relevance rather than relying exclusively on vector similarity.

### 8. Prompt Construction

The retrieved information is transformed into contextual information for the LLM.

A custom prompt-building component is responsible for constructing the final prompt sent to the model.

The prompt instructs the model to use the retrieved context as the source of truth.

### 9. Response Generation

The system uses Azure OpenAI to generate the final response.

The model receives:

- The employee's question.
- The retrieved and ranked context.
- The system instructions defined by the application.

If the retrieved context does not contain enough information to answer the question reliably, the system instructs the model to respond that it does not know rather than inventing an answer.

This provides a basic grounding mechanism to reduce hallucinations.

## 🧩 Knowledge Lifecycle

The project also includes APIs for managing the knowledge stored in the RAG system.

Knowledge can be:

- Ingested.
- Deleted individually using its identity.
- Completely removed when necessary.

This makes the vector store manageable as the underlying company knowledge evolves.

## 🔌 REST API

### Chat

```http
POST /api/chat
```

Sends an employee question to the RAG system and returns the generated response.

Example:

```json
{
  "message": "What is the company's leave policy?"
}
```

### Ingest Knowledge

```http
POST /api/knowledge/ingest
```

Triggers the ingestion of a knowledge source.

The ingestion pipeline extracts, chunks, embeds, and stores the resulting knowledge in Redis.

### Delete Knowledge

```http
DELETE /api/knowledge/{identity}
```

Deletes the knowledge associated with a specific identity.

This is useful when a document or knowledge source has been updated or removed.

### Delete All Knowledge

```http
DELETE /api/knowledge
```

Removes all indexed knowledge from the vector store.

### Health Check

```http
GET /health
```

Returns the application's health status.

```text
OK
```

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java | Backend development |
| Spring Boot | Application framework |
| Spring AI | AI/RAG integration |
| Redis | Vector storage and similarity search |
| PostgreSQL | Relational data storage |
| Azure OpenAI | Large Language Model |
| Google Embeddings | Vector embeddings |
| REST API | Application communication |
| Docker | Containerization, if applicable |

## 🏛️ Backend Architecture

The backend is organized around distinct responsibilities rather than putting the entire RAG workflow into a single service.

The main conceptual components are:

```text
Controller
    │
    ▼
Service Layer
    │
    ├── Ingestion
    │
    ├── Chunking
    │
    ├── Embedding
    │
    ├── Vector Store
    │
    ├── Retrieval
    │
    ├── Ranking / Re-ranking
    │
    └── Prompt Construction
            │
            ▼
           LLM
```

This separation makes the system easier to maintain and allows individual stages of the RAG pipeline to evolve independently.

## 🔐 Grounding & Hallucination Control

One of the main design goals is to prevent the LLM from freely generating information that is not supported by the company's knowledge base.

The system therefore follows a grounded-generation approach:

```text
User Question
      ↓
Retrieve Relevant Knowledge
      ↓
Rank Retrieved Context
      ↓
Provide Context to LLM
      ↓
Generate Answer From Context
      ↓
Insufficient Context?
   ↙               ↘
 Yes                No
 ↓                  ↓
"I don't know".    Answer
```

The LLM is not treated as the primary knowledge source. Instead, it acts as the reasoning and response-generation layer on top of the retrieved enterprise knowledge.

## 🎯 Key Engineering Features

- Retrieval-Augmented Generation architecture.
- Multiple knowledge-source ingestion.
- Source-aware chunking.
- Semantic vector search.
- Top-K retrieval.
- Metadata filtering.
- Metadata-based ranking/re-ranking.
- Recency-aware ranking.
- Redis vector storage.
- Custom prompt construction.
- Knowledge lifecycle management.
- Grounded LLM responses.
- RESTful backend API.
- Separation of ingestion, retrieval, ranking, and generation responsibilities.

## 🚀 Why This Project?

This project was built to explore how modern AI systems can be integrated into enterprise backend applications while maintaining control over where the generated information comes from.

Beyond integrating an LLM, the project focuses on the engineering challenges surrounding RAG systems:

- How information should be ingested.
- How different sources should be represented.
- How documents should be chunked.
- How relevant information should be retrieved.
- How retrieved information should be ranked.
- How metadata can improve retrieval quality.
- How to manage changing knowledge.
- How to reduce hallucinations through grounding.

The project therefore combines traditional backend engineering with modern AI engineering concepts.

## 🔮 Future Improvements

Potential future improvements include:

- Adding more enterprise knowledge sources.
- Improving re-ranking strategies.
- Adding response citations pointing to the original source.
- Introducing evaluation datasets for measuring retrieval quality.
- Adding observability and RAG-specific metrics.
- Improving access control and user-specific knowledge filtering.
- Adding conversational memory.
- Implementing automated knowledge updates.
- Improving handling of time-sensitive information.

## 👨‍💻 Project Focus

SBS Corp Agent demonstrates the integration of:

**Backend Engineering + AI Engineering + RAG + Information Retrieval + Enterprise Knowledge Management**

The main objective is not simply to build a chatbot, but to design a maintainable backend architecture capable of transforming heterogeneous enterprise knowledge into reliable, searchable, and grounded AI responses.
