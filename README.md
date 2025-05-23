# Trabalho 1 – Comunicação entre processos (Capítulo 4)

## Universidade Federal do Ceará – Campus de Quixadá  
**Disciplina:** QXD0043 - Sistemas Distribuídos  
**Cursos:** SI, RC, ES, CC, EC e DD  
**Professor:** Rafael Braga  

---

## **Sockets e Streams**

### **1. Definição do Serviço Remoto**
- Cada estudante deve definir um serviço remoto. ([sugestões](https://docs.google.com/document/d/195cAAxlmFoZ_mAgTuPGF7eD28e8-5zBA8_rSiGol59I/edit?tab=t.0))  
- Criar pelo menos **classes POJO** que representem as informações do serviço escolhido.  
- Implementar **2 classes de modelo** que representem serviços.  

### **2. Implementação de `PojoEscolhidoOutputStream`**
- Escolha uma classe POJO e transforme-a em uma subclasse de `OutputStream` (ex: `PojoEscolhidoOutputStream`).  
- **Construtor deve receber:**  
  - Um array de objetos (dados a serem transmitidos).  
  - O número de objetos a serem enviados.  
  - Para cada objeto, envie o número de bytes usados para gravar pelo menos 3 atributos.  
  - Um `OutputStream` de destino.  
- **Testes:**  
  - Saída padrão (`System.out`).  
  - Arquivo (`FileOutputStream`).  
  - Servidor remoto (TCP).  

### **3. Implementação de `PojoEscolhidoInputStream`**
- Crie uma subclasse de `InputStream` chamada `PojoEscolhidoInputStream` para ler os dados gerados anteriormente.  
- **Construtor deve receber:**  
  - Um `InputStream` de origem.  
- **Testes:**  
  - Entrada padrão (`System.in`).  
  - Arquivo (`FileInputStream`).  
  - Servidor remoto (TCP).  

---

## **Serialização**

### **4. Serviço Remoto Cliente-Servidor**
- Implemente um serviço remoto usando comunicação via sockets (TCP ou UDP).  
- **Serialização:**  
  - Cliente empacota a mensagem de **request** antes de enviar.  
  - Cliente desempacota a mensagem de **reply** recebida.  
  - Servidor desempacota a mensagem de **requisição** do cliente.  
  - Servidor empacota a mensagem de **reply** antes de enviar.  

---

## **Representação Externa de Dados**

### **5. Sistema de Votações Distribuído**
- **Funcionalidades:**  
  - **Eleitores:**  
    - Fazem login.  
    - Recebem lista de candidatos.  
    - Enviam votos dentro de um prazo máximo.  
  - **Administradores:**  
    - Adicionam/removem candidatos.  
    - Enviam notas informativas via multicast (UDP).  
- **Comunicação:**  
  - **Unicast (TCP):** Login, lista de candidatos e votos.  
  - **Multicast (UDP):** Notas informativas.  
- **Servidor:** Multi-threaded.  
- **Representação de dados:** Protocol Buffers (aceito XML ou JSON).  

---

**Figura 1:** Arquitetura da aplicação, utilizando Java Sockets.  

**Bom trabalho!**  
