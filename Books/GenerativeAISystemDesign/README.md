# Generative AI System Design Interview

Chapter 1 : Introduction and Overview 

![Screenshot 2025-12-20 at 2.30.06 PM.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-20_at_2.30.06_PM.png)

A ML model’s effectiveness depends on its training data. If a model has not been trained on good data, it will have difficulty executing operations. Improving a model for a specific task requires large datasets with labels, but collecting this data can be challenging and expensive. 

GenAI’s success is driven by self supervised learning, where it can learn from unlabeled data and use datasets from the internet without the need of labeling. Models have easy access to very large datasets from the internet, therefore these GenAI models can be trained on these massive datasets. 

These models learn based on model capacity which is measured by **number of parameters and FLOP (FLOATING POINT OPERATIONS) count.** 

The **number of parameters** is the indicator of a model’s capacity to learn from data. More parameters means the model has a better capability of learning complex patterns and relationships that exist within the data. 

The FLOP is the computation complexity of a model and what is required to complete a forward pass as data moves through the model layers. 

Compute power is powered by CPUs, GPUS, and TPUs. The performance of these machines is measured in FLOP/S. Single machines are not capable of meeting the needs to train models. Therefore distributed training has been crucial (allowing the workload to be shared across thousands of machines in parallel). 

**Functional requirements:** the core capabilities of the system. 

**Non-functional requirements:** how the system performs (e.g latency, throughput, fairness, security, and scalability), but now what it does.

**Structured data:** data that can be organized into tables with rows and columns (e.g a database or a spreadsheet; financial records). 

- categorical data (e.g gender or color)
- numerical data (e.g number of items sold, house price).
- ordinal data: data with a predetermined order (e.g satisfaction ratings).

**Unstructured data:** data with no underlying data schema or structure, such as text, images, videos, audio files, etc. Social media pots or emails are examples of unstructured data. 

For traditional models, you need data engineering and feature engineering. 

Data engineering involves building and maintaining systems for collecting, storing, retrieving, and processing data. A core component of this is ETL (extract, transform, load) which is the process of **extracting** data from various sources, **transforming** it into a usable format, and **loading** it into a data warehouse or storage system. Data engineering ensures that the data is clean, reliable, and accessible. 

Feature engineering involves selecting and extracting predictive features from raw data and transforming them into a format usable by ML models (e.g Tectcon, Amazon SageMaker). 

![Screenshot 2025-12-20 at 2.46.08 PM.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-20_at_2.46.08_PM.png)

![Screenshot 2025-12-20 at 2.49.00 PM.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-20_at_2.49.00_PM.png)

There is a trend toward enhancing training datasets with AI-generated content. This involves using existing models to create synthetic data, which is then used to train another GenAI model. 

### **Pros:**

- **Improving data diversity:** AI-generated content adds variety to existing data, thus enhancing the model's ability to generalize, especially when the original data is limited or imbalanced.
- **Scalability:** As demand for data grows, AI-generated content provides a scalable way to create large datasets that are difficult to gather manually.

### **Cons:**

- **Quality concerns:** The quality of synthetic data depends on the original model. Poor-quality data can lead to the spread of biases or errors.
- **Representation issues:** The synthetic data might not represent the original data well. Ensuring the synthetic data is diverse and representative can be challenging.
- **Real-world distribution gaps:** AI-generated data may not fully capture the complexity of real-world scenarios, thereby risking the omission of important details.

Data cleaning

![Screenshot 2025-12-20 at 2.51.11 PM.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-20_at_2.51.11_PM.png)

### **Efficient storage**

Storing massive amounts of data with traditional tools can be expensive and slow. Distributed storage systems such as Hadoop Distributed File System (HDFS) [26] and Amazon S3 [27] are built to store massive amounts of data across multiple machines. These systems are particularly suited for managing large volumes of unstructured data. In addition, columnar storage formats such as Parquet [28] and ORC [29] are ideal for structured data or unstructured data that has been converted to structured form. These formats, optimized for analytics, offer better compression and faster query performance.

### **Efficient retrieval**

Training an ML model requires fast data retrieval. Common techniques to retrieve data efficiently from large datasets include:

- **Sharding:** Splitting data across multiple devices allows parallel access and speeds up retrieval and processing.
- **Indexing:** Technologies such as Apache Lucene [30] or Elasticsearch [31] are used to index data, making it easy and quick to locate specific pieces of information.
- **Pre-loading or caching:** Frequently accessed data is pre-loaded into memory to reduce I/O delays during retrieval.

People clean data *before* writing Parquet, because Parquet is designed to store data that’s already structured and trustworthy.

For training large scale models the three most common techniques are:

- Gradient checkpointing: reduce memory usage during model training by only using a selected subset of activations. During a backward pass, the missing activations are recomputed. (Use this if limited GPU memory).
- Mixed precision training: Use 16bit (half precision) and 32bit (single precision) floating point numbers to speed up model training and reduce memory usage. Perform most calculations at lower precision; and crucial operations at higher precisions when needed.
- Automatic mixed precision: used by PyTorch and TensorFlow. Handle the transition between half and single precision, optimizing where to use each precision type and applying scaling techniques to maintain numerical stability during training.

As models grow in size and complexity, training on a single machine becomes infeasible. Distributed training techniques need to scale, and you can do this by utilizing multiple machines or devices in parallel. 

![Screenshot 2025-12-20 at 4.07.06 PM.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-20_at_4.07.06_PM.png)

Data parallelism: data set is split across multiple devices (e.g GPU), each of which holds a full copy of the model and processes a portion of the data in parallel. Each device trains on its subset of data, and the parameter server coordinates the updating and distributing of model parameters across all devices. 

![Image represents a distributed training system for a machine learning model.  The system features a central 'Parameter Server' at the top, which communicates bidirectionally with multiple instances of 'Model A,' each residing on a separate GPU (GPU 0, GPU 1, ..., GPU N).  Each Model A instance is depicted as a cloud symbol within a box labeled with its corresponding GPU number.  Below each GPU, a database-like cylinder represents local data storage for that GPU.  All the GPUs draw data from a shared 'Dataset' represented by a cluster of cylinders at the bottom.  Arrows indicate the flow of information: the Parameter Server sends model parameters to each Model A instance, receives updated parameters back, and the GPUs receive data from the shared Dataset.  The ellipses ('...') indicate that the depicted structure repeats for an unspecified number of GPUs.](https://bytebytego.com/images/courses/genai-system-design-interview/introduction-and-overview/figure-1-21-L3K2XU2A.svg)

Figure 21: Data parallelism

Synchronous: all devices complete their computations and send their gradients over to the parameter server. The parameter server waits until it has received gradients from every device and then it aggregates and updates the model before sending the updating parameters back to all devices. This ensures consistency as the devices always work with the same version of the model. 

Asynchronous: Each device sends its gradients to the parameter server as soon as it finishes processing its portion of data, and the parameter server updates the model immediately upon receiving gradients from any devices and sends the new parameters to all devices. This approach is faster then synchronous but can lead to inconsistency. 

*alternatives Model Parallelism, Pipeline Parallelism, Tensor Parallelism. 

Chapter 2 : Gmail Smart Compose 

Train the model using excerpts from books, textbooks, websites and emails. First step is to do some **text cleaning:**

- remove non-english text
- remove confidential information (e.g urls, personal names, email addresses, phone numbers). We do not want the data model to accidentally expose these.
- remove irrelevant characters or symbols.
- remove duplicated data ( we must do this so that the data model does not become biased and so it does not skew the model’s learning process).

Then do **text normalization** (e.g convert all phone numbers into a consistent format). 

Afterwards tokenize the words. There are three tokenizer strategies (e.g character-level, word-level, and subword-level). Most LLMs use subword-level. Then convert these subwords into their integer form. 

Directly training the model on task-specific dataset, e.g email data is NOT a good strategy. 

- Lack of large training data: task-specific datasets are limited in size. This limitation can hinder the model’s ability to learn effectively.
- Risk of overfitting: Overfitting occurs when a model memorizes the training data to the extent that it cannot generalize to unseen data.
- Expensive and lengthy training: Training a large model from scratch requires significant computation resources and time. This is because the model has to learn different aspects of language, which is a complex and resource-intensive process.

A two stage training strategy is commonly employed: pre training and then fine tuning. 

In pre training the model is trained on a large amount of general data to learn the structure of the language. In the fine tuning stage, the pre trained model is then fine tuned to the specific task at hand. 

![Screenshot 2025-12-20 at 4.43.25 PM.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-20_at_4.43.25_PM.png)

**Sampling i**s the process of using a trained generative model to generate new data. In the book there are multiple examples Deterministic, Stochastic. 

Deterministic has two algorithms : greedy search and beam search. 

**Evaluating the data model.** You should do this in an offline and online. 

For offline evaluation metrics, use perplexity and ExactMatch@N. 

Perplexity is a standard metric used extensively in the offline evaluation of language models. This metric measures how accurately the model predicts the exact sequence of tokens present in text data. In mathematical terms, perplexity is defined as the exponential of the average "negative log-likelihood" of the predicted probability given the previous tokens in a sequence. 

ExactMatch@N measures the percentage of generated phrases that are exactly N words long and that match the first N words of the ground-truth text

**Both evaluation approaches requires a lot of math.** 

For online evaluation metrics: user engagement (e.g acceptance rate : the % of suggestions accepted by users, usage rate), effectiveness (e.g average completion time to write an email reply), latency metrics (e.g system response time : time it takes Smart Compose suggestions to appear after the user begins typing. Its important to ensure this metric stays below a certain threshold so the suggestions are made before the user types them), quality metrics (e.g feedback rate; human evaluation). 

---

Triggering service: When should Smart Compose execute? It should execute after the user types in a few words (e.g “I hope”). If you do it to early (e.g “I”) there are to many possibilities of where this email can go, therefore its difficult for the data model to provide suggestions. 

Phrase generator: Core. It generate the most likely completion based on the partial text the user has already typed. 

![Screenshot 2025-12-20 at 4.59.19 PM.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-20_at_4.59.19_PM.png)

1. **Monitoring:** The triggering service monitors the user's activity as they type.
2. **Triggering:** The service triggers the phrase generator once it identifies specific patterns.
3. **Beam search:** The phrase generator employs beam search to get top-k potential completions from the trained model.
4. **Filtering:** The phrase generator interacts with the filtering component to remove long suggestions and those with low confidence scores.
5. **Post-processing:** The completion with the highest score is picked and passed to the post-processing service. The service replaces gender-specific pronouns and adjusts sensitive terms.
6. **Display suggestion:** The suggestion is displayed to the user for their consideration.

Chapter 3 : Google Translate 

Byte Pair Encoding is complex, but solves several hard problems. 

- Open vocabulary: any string can be represented
- Compression-like behavior: frequent patterns get shorter encodings.
- Language-agnostic: works across languages without custom rules.
- Efficient of neural nets: fewer tokens = faster models.
- BPE is like a data driven compression scheme that happens to produce symbols useful for language modeling.
    
    ![Screenshot 2025-12-25 at 3.34.27 PM.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-25_at_3.34.27_PM.png)
    

Chapter 4 : ChatGPT: Personal Assistant Chatbot 

Chatbots are designed to handle follow up questions (e.g a chatbot can maintain the same conversation session with a context window of at least 4096 tokens. If token limits are getting close, the chatbot will implement a sliding window algorithm which will compress and summarize earlier messages). 

Large language models (LLMs) often have billions of parameters to learn effectively. **The decoder-only transformer is the standard architectural choice in language models.** 

Data preparation:

- Get data from websites, forums, blogs.
    - content extraction and parsing: remove all the unnecessary information using raw HTML content libraries (e.g Beautiful Soup).
    - use url and domain filtering to skip low quality websites or spam.
    - language identification : filter to match the target language for training.
    - content quality filtering : implement quality assessment techniques (e.g readability scoring, spam detection algorithms, heuristic checks to evaluate and filter low-quality text).
    - remove inappropriate content and low quality data
    - anonymize sensitive information
    - remove duplicate data : this step reduces redundancy in the training dataset and ensures the model is not overexposed to certain data.
    - remove irrelevant data
    - tokenize text using Byte Pair Encoding.

![Screenshot 2025-12-25 at 3.47.28 PM.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-25_at_3.47.28_PM.png)

Advanced chatbots use a three-stage training strategy.

![Screenshot 2025-12-25 at 3.50.20 PM.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-25_at_3.50.20_PM.png)

1. Pretraining: a model is trained with an enormous volume of text data from the internet. The goal is to create a base model with a broad understanding of language and world knowledge. This stage requires thousands of GPUs, millions of dollars, and months of training. There are a few data sets that are available for the model to be trained on. 
    1. The outcome of the pretraining stage is a model that understands language well and can finish a sentence given a prompt. 
2. SFT (Supervised FineTuning): Finetune the base model on a smaller high-quality dataset in a (prompt response) format. Preserve the base model’s language-understanding and world knowledge while adapting its behavior to responding to prompts instead of just continuing them. The model is trained using “demonstration data” which is significantly smaller, but the quality of the data is carefully curated. 1/3 of OpenAi’s labelers for GPT’s demonstration dataset held a master’s degree. 
    1. The outcome is a fine tuned version of the base model. Instead of continuing the text prompt, the SFT model generates detailed and helpful responses because it has been trained on demonstration data. 
    2. The SFT model generates a grammatically correct and reasonable answer. However it may not be the “best answer”. The answers may be unhelpful or unsafe. 

![Screenshot 2025-12-25 at 3.57.20 PM.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-25_at_3.57.20_PM.png)

1. RLHF (aka the alignment stage). Align the model to human preferences. Adapt the model to generate responses preferred by humans. To do this, you have to train a reward model that evaluates the relevance of a response to a prompt. This model takes a (prompt, response) pair as input and produces a score predicting the helpfulness of the response. Training a model to output a score is a very common task in ML. There are various architectures we can employ for reward modeling: It can be a decoder-only, encoder-only, or encoder-decoder Transformer as long as it outputs a scalar value.

To collect training data for reward modeling, we follow these steps:

1. **Collect prompts:** Manually create a list of prompts.
2. **Generate multiple responses:** Use the SFT model to generate multiple responses for each prompt.
3. **Rank responses:** Ask contractors to evaluate those responses and rank them based on their relevance. The reason they typically rank them instead of scoring each response is that ranking reduces subjectivity and inconsistency. It is easier and more intuitive for annotators to compare responses directly than to assign numerical scores, which can vary between annotators. This approach simplifies the evaluation process and ensures more reliable data for training.
4. **Create preference pairs:** Construct the training dataset by forming pairs in the format (prompt, winning response, losing response). In each pair, the winning response is preferred over the losing response based on the rankings from the previous step.

[Image represents a system for evaluating responses from a Large Language Model (LLM).  The process begins (1) with a prompt list containing questions like 'What is the capital of France?', 'Name a famous physicist?', 'What's 2 + 2?', and 'Give a synonym for 'happy.''.  These prompts are fed (2) into an 'SFT Model' (likely a fine-tuned large language model), which generates three different responses (Response 1, Response 2, Response 3) for each prompt.  A human evaluator (3) then reviews these responses and determines a 'winning' and 'losing' response for each prompt, based on accuracy and quality. This information is compiled (4) into a table showing the winning and losing responses for each prompt. Finally, the evaluator provides rankings (R1 > R2 > R3, for example) indicating the relative quality of the three responses for each prompt, providing feedback on the LLM's performance.  The entire diagram illustrates a human-in-the-loop evaluation process for ranking the quality of LLM responses.](https://bytebytego.com/_next/image?url=%2Fimages%2Fcourses%2Fgenai-system-design-interview%2Fchatgpt-personal-assistant-chatbot%2Ffigure-4-14-W7RQHVNH.png&w=3840&q=75)

Figure 14: Collecting training data to train a reward model

The outcome of the RLHF stage is a model that can be deployed as a chatbot. 

![Screenshot 2025-12-25 at 4.02.41 PM.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-25_at_4.02.41_PM.png)

![Image represents a flowchart illustrating the stages involved in training a large language model (LLM), specifically highlighting the progression from a base model to a refined model using reinforcement learning.  The flowchart is divided into four main columns representing distinct training stages: Pretraining, Supervised Finetuning, Reward Modeling, and Reinforcement Learning. Each stage consists of three rows detailing the dataset used, the computational resources employed (number of GPUs), and the algorithm applied.  The Pretraining stage uses internet data, thousands of GPUs, and a language modeling algorithm to create a 'Base Model' (examples: GPT, Llama, PaLM).  The Supervised Finetuning stage takes the Base Model as input, utilizes demonstration data and 1-100 GPUs with a language modeling algorithm to produce an 'SFT Model' (example: Vicuna-13B).  The Reward Modeling stage uses comparisons data, 1-100 GPUs, and a regression algorithm to create a 'Reward Model' based on the SFT Model. Finally, the Reinforcement Learning stage uses prompts, 1-100 GPUs, and a reinforcement learning algorithm, taking both the SFT Model and the Reward Model as input, to generate an 'RL Model' (examples: ChatGPT, Gemini). Arrows indicate the flow of information and the model's progression through each stage.  Each stage's input and output are clearly labeled, along with the computational resources and algorithms used.](https://bytebytego.com/images/courses/genai-system-design-interview/chatgpt-personal-assistant-chatbot/figure-4-18-ABXYUHNY.svg)

Figure 18: Summary of LLM training, inspired by [29]

Deterministic Methods:

- greedy
- beam search

Stochastic methods

- multinomial sampling
- top k-sampling
- top-p sampling

To evaluate LLMs, the following tasks are evaluated 

- Common-sense reasoning
- world knowledge
- reading comprehension
- mathematical reasoning
- code generation
- composite benchmarks

Safety evaluation are critical and are used to ensure these models generate responses that are safe and ethical. 

- Toxicity and harmful content.
- Bias and fairness
- Truthfulness
- User privacy and data leakage
- Adversarial robustness.

https://openlm.ai/chatbot-arena/

[https://lmarena.ai/](https://lmarena.ai/) 

Chapter 5 : Image Captioning 

Convolutional neural networks (CNNs) are traditionally used for image-encoding tasks. 

**Image processing requires a lot of vectors and a lot of math.** 

![Screenshot 2025-12-26 at 14.54.09.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-26_at_14.54.09.png)

Chapter 6: Retrieval-Augmented Generation

![Screenshot 2025-12-26 at 15.03.00.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-26_at_15.03.00.png)

Prompt engineering  is simple and flexible way to guide a general purpose LLM without fine tuning, however its not scalable. The information from all external sources in the prompt will most likely exceed the LLM’s context window. 

A better solution is to use RAG. 

![Screenshot 2025-12-26 at 15.05.35.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-26_at_15.05.35.png)

The performance of the RAG system relies on the quality of the knowledge database and the way it is indexed. When the knowledge database is sourced from websites, data-cleaning strategies such as removing inappropriate content or anonymizing sensitive information should be applied. 

Parsing documents (e.g PDFs) can be tricky. Therefore several online services provide document parsing services, for example, Google Cloud Document AI [7] and [PDF.co](http://pdf.co/) [8]. These services allow users to upload their documents and have them parsed without needing to set up and maintain the parsing system themselves.

**Indexing is the process of organizing the chunked data into a structure that enables efficient and accurate retrieval. Here are a few common retrieval techniques.**

- keyword based “match query terms with the content of the documents”.
- full-text search: elastic search. This has a high computational overhead, especially if you are working with millions of documents. This is less effective compared to semantic retrieval.
- knowledge graph based
- vector based : high dimensional embeddings — numerical representations of the text and images - to measure the similarity between a query and the stored chunks of data. This technique enables the retrieval of relevant information even when the exact words in the query do not match the document content, making it more flexible and powerful for large scale datasets.

Traditional retrieval methods: keyword-based, full-text search have been widely used but they face limitations in speed, scalability, and the ability to understand the semantic meaning of queries. Knowledge graph-based retrieval queries require significant effort to build and maintain such graphs, making them a costly choice 

Many RAG systems use the vector based approach because:

- semantic understanding: it can capture the semantic meaning of a query, allowing for more accurate retrieval even when the exact query terms are not present in the document.
- scalability: using embedding vectors makes this method highly scalable and able to handle large datasets efficiently.
- efficiency: once the data is indexed as embedding vectors, the system can retrieve relevant chunks.

![Screenshot 2025-12-26 at 15.17.23.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-26_at_15.17.23.png)

Prompt Engineering revisited: The goal is to guide the moel’s output to better align with specific tasks.

1. Start simple: begin with straightforward prompts an gradually introduce more complexity. 
2. break down complex tasks: break down tasks involving multiple subtasks into smaller manageable steps. This avoids overwhelming the LLMs and ensures better focus on individual tasks. 
3. Use clear instructions (e.g “Write, Summarize, Translate”). Placing instructions at the beginning of the prompts separated by delimiters such as ‘###’, can help organize the prompt. 
4. Be specific. 

Prompt strategies:

- chain of thought: involves guiding the model through intermediate reasoning steps before arriving at a final answer. This is especially useful for complex queries requiring multi-hop reasoning, where the model must combine information from multiple documents to generate a complete response. CoT prompts guide the model to break down its reasoning into steps, leading to more accurate and insightful answers.
- few-shot prompting: involves providing the model with a few examples of input-output pairs before the actual query.
- role-specific prompting: the language model may need to adopt a specific "role" to generate an appropriate response. For example, in legal or medical domains, prompting the model to act as a subject-matter expert ensures that the response carries the necessary tone, accuracy, and authority.
    
    ![Screenshot 2025-12-26 at 15.26.33.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-26_at_15.26.33.png)
    

Chapter 7: Realistic Face Generation 

- Variational autoencoder
- Generative adversarial network
- Autoregressive model : This is used by OpenAI’s DALL-E and Google’s Muse
- Diffusion
- There are lot of pros and cons of each. So if you are curious about it, search it. There is no way I am going to remember how each of these are used and implemented.

Chapter 8: High-Resolution Image Synthesis 

Chapter 9: Text-to-Image Generation 

Autoregressive models treat text-to-image generation as a sequence generation task. A decoder-only Transformer takes a sequence of text tokens as input and outputs a sequence of visual tokens representing an image. An image tokenizer then decodes these visual tokens into the actual image. 

Diffusion models is the alternative approach. Diffusion models approach it as an iterative refinement process. 

![Screenshot 2025-12-27 at 15.44.47.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-27_at_15.44.47.png)

Chapter 10: Personalized Headshot Generation 

- Textual Inversion
- LoRA
- DreamBooth

Chapter 11: Text-to-Video Generation 

![Screenshot 2025-12-28 at 08.34.20.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-28_at_08.34.20.png)

![Screenshot 2025-12-28 at 08.37.38.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-28_at_08.37.38.png)

![Screenshot 2025-12-28 at 08.37.50.png](Generative%20AI%20System%20Design%20Interview/Screenshot_2025-12-28_at_08.37.50.png)

Chapter 12: Text-To-Video Generation 

- DiT
- U-Net
- Diffusion model (image vs video).