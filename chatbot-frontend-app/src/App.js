import { useState } from "react";
import "./App.css";
import ReactMarkdown from "react-markdown";
import GeminiService from "./Components/GeminiService";

const App = () => {
  const [value, setValue] = useState("");
  const [error, setError] = useState("");
  const [chatHistory, setChatHistory] = useState([]);

  const getResponse = async () => {
    if (!value) {
      setError("Error: Please ask a question");
      return;
    }
    try {
      const newMessage = { role: "user", parts: value };
      const updatedChatHistory = [...chatHistory, newMessage];

      const data = await GeminiService.sendMessages(updatedChatHistory);

      setChatHistory((oldChatHistory) => [
        ...oldChatHistory,
        newMessage,
        {
          role: "model",
          parts: data,
        },
      ]);
      setValue("");
    } catch (error) {
      setError("Error: Something went wrong");
    }
  };

  const clear = () => {
    setValue("");
    setError("");
    setChatHistory([]);
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
      getResponse();
    }
  };

  return (
    <div className="app">
      <h1 className="heading">Spring Boot AI Chatbot</h1>
      <div className="chat-container">
        <div className="chat-messages">
          {chatHistory.map((chatItem, _index) => (
            <div key={_index} className={`chat-item ${chatItem.role}`}>
              <div className="chat-bubble">
                <p className="chat-role">
                  {chatItem.role.charAt(0).toUpperCase() +
                    chatItem.role.slice(1)}
                  :
                </p>
                <ReactMarkdown>{chatItem.parts}</ReactMarkdown>
              </div>
            </div>
          ))}
        </div>
        <div className="input-container">
          <input
            value={value}
            placeholder="Type your question here"
            onChange={(e) => setValue(e.target.value)}
            onKeyDown={handleKeyDown}
          />
          <button onClick={getResponse} className="btn btn-primary">
            Ask Me
          </button>
          <button onClick={clear} className="btn btn-secondary">
            Clear Chat
          </button>
        </div>
        {error && <p className="error">{error}</p>}
      </div>
    </div>
  );
};

export default App;
