# 📌 Members Book - 🌐 Enjoy

O **Members Book** é um aplicativo **MOBILE** voltado para conectar líderes de diferentes setores, fomentando negócios, parcerias e networking.  
Ele foi projetado para ser **escalável, seguro e intuitivo**.  

Apesar de MVP, este se mostra somente na parte **navegável**, pois a **modelagem realizada já prevê diversas funcionalidades**, tais como:  

1. **Relatórios de faturamento por período** → Permite a visualização do valor que o aplicativo gera ao membro.  
2. **Negócios Fechados** → Contratos firmados entre membros visando fortalecer o retorno do networking interno do app.  
3. **Modelagem para Chat interno e visitante** → Não está aplicado neste MVP, porém a modelagem já prevê essa possibilidade (por meio dos **DTOs**).  

Devido ao curto tempo, aplicamos um **MVP funcional** com um fluxo que visa solucionar uma das maiores **dores da empresa**:  
a **padronização de bios**.  

O restante da aplicação como um todo que este backend possibilita, está **prototipado no projeto Figma**.  

---

## 🔄 Fluxo do Usuário

1. Entrar no aplicativo  
2. Cadastrar suas informações (formulário totalmente **personalizável**)  
3. Finalizar cadastro  
4. Visualizar o novo membro com a **bio personalizada gerada automaticamente**  

---

## ✨ Geração de Bio com IA

A bio é gerada a partir das respostas do formulário de cadastro que o usuário preenche, juntamente à uma **integração com inteligência artificial**,  
que **trata, normaliza e padroniza os dados** para manter consistência entre os perfis. **Já está funcional**

---

# 📊 Escalabilidade

Este backend foi projetado pensando em **crescimento contínuo** e **alta disponibilidade**, garantindo que a aplicação possa evoluir sem comprometer a performance ou a experiência do usuário.

---

## 🗄️ Banco de Dados

- Criação de **índices em campos de busca frequente** para acelerar consultas.  
- **Paginação em consultas** para evitar sobrecarga e otimizar o consumo de memória.  
- Possibilidade de **replicação e particionamento** do banco de dados em cenários de produção.  

---

## 🏗️ Arquitetura

- **Camadas bem definidas** seguindo o padrão:  
  `Controller → Service → Repository`  
- Suporte a **múltiplas instâncias em paralelo** (**horizontal scaling**) com balanceamento de carga.  
- Estrutura modular que permite a **evolução independente de funcionalidades** (novos módulos podem ser adicionados sem quebrar os existentes).  

---

## 🚀 Benefícios da Escalabilidade

- **Performance estável** mesmo com aumento significativo de usuários.  
- **Alta disponibilidade** por meio de deploy em múltiplas instâncias.  
- **Facilidade de manutenção** e expansão da aplicação.  

---

# 📈 Roadmap (Futuras Evoluções)

Este backend foi projetado para evoluir de forma modular, permitindo a adição de novas funcionalidades sem impactar o sistema atual.  

## 🔔 Sistema de Notificações em Tempo Real
- Alertas sobre novas mensagens, indicações ou atualizações de perfil.
- Notificações push para mobile e desktop.

## 🤖 Integração com IA Generativa
- Sugestões inteligentes de networking.
- Recomendações de conexões.

## 💬 Chat Interno
- Conversas privadas entre membros.
- Suporte a mensagens em tempo real.
- Histórico de conversas e envio de arquivos.

## 🎯 Sistema de Indicações e Gamificação
- Membros podem indicar outros líderes (especie de "curtida" no perfil).
- Registro de quem indicou quem, servindo de base para **ranking de membros**.
- Pontuação e recompensas com base nas indicações e negócios fechados.
- Destaques como “Recomendação Diamante 💎” para membros mais ativos, tanto no quesito de contratos firmados quanto no de mais indicados.

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Hibernate**
- **Lombok**
- **SQL Server**
- **Spring Security**
- **Spring Boot Dev Tools**
- **Maven**

---

## ⚙️ Requisitos

Antes de rodar o projeto, certifique-se de ter instalado:

- **Java 17+**
- **Maven 3.8+**
- Banco de dados **SQL Server** configurado

---

## ▶️ Como executar o projeto

```bash
# Clonar o repositório
git clone https://github.com/Felippe-glitch/inovapps_20205.git

# Entrar na pasta onde está o pom.xml
cd demo

# Executar a aplicação
mvn spring-boot:run
