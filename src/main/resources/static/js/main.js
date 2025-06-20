'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

const MessageType = Object.freeze({
    JOIN: "JOIN",
    CHAT: "CHAT",
    LEAVE: "LEAVE"
});

function connect(event) {
    username = document.querySelector('#name').value.trim();

    if (isUsernameValid(username)) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        let socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }

    event.preventDefault();
}

function isUsernameValid(username) {
    console.log("Username: " + username);

    if (!username) {
        console.error("Empty or null username");
        return false;
    }

    const MINIMUM_LENGTH_OF_USERNAME = 3;
    const MAXIMUM_LENGTH_OF_USERNAME = 30;

    if (username.length < MINIMUM_LENGTH_OF_USERNAME || username.length > MAXIMUM_LENGTH_OF_USERNAME) {
        console.error("Invalid username length");
        return false;
    }

    return true;
}

function onConnected() {
    stompClient.subscribe('/topic/public', onMessageReceived);
    stompClient.subscribe('/topic/errors', onErrorReceived);

    let chatMessage = {
        sender: username,
        type: MessageType.JOIN
    };

    stompClient.send("/app/chat/users", {}, JSON.stringify(chatMessage));
    connectingElement.classList.add('hidden');
}

function onError(error) {
    console.error(error);
    connectingElement.textContent = 'Failed to connect to server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function sendMessage(event) {
    let messageContent = messageInput.value.trim();

    if (messageContent && stompClient) {
        let chatMessage = {
            sender: username,
            content: messageInput.value,
            type: MessageType.CHAT
        };

        stompClient.send("/app/chat/messages", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }

    event.preventDefault();
}

function onErrorReceived(error) {
    console.error("Error received from the server: " + error);
}

function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);
    let messageElement = document.createElement('li');

    try {
        let messageType = message.type;
        console.log("Message type: " + messageType);

        switch (messageType) {
            case MessageType.JOIN:
                messageElement.classList.add('event-message');
                message.content = message.sender + ' joined!';
                break;
            case MessageType.LEAVE:
                messageElement.classList.add('event-message');
                message.content = message.sender + ' left!';
                break;
            case MessageType.CHAT:
                messageElement.classList.add('chat-message');

                let avatarElement = document.createElement('i');
                let avatarText = document.createTextNode(message.sender[0]);

                avatarElement.appendChild(avatarText);
                avatarElement.style['background-color'] = getAvatarColor(message.sender);

                messageElement.appendChild(avatarElement);

                let usernameElement = document.createElement('span');
                let usernameText = document.createTextNode(message.sender);
                
                usernameElement.appendChild(usernameText);
                messageElement.appendChild(usernameElement);
                break;
            default:
                throw new Error("Unknown message type");
        }

        let textElement = document.createElement('p');
        let messageText = document.createTextNode(message.content);
        
        textElement.appendChild(messageText);
        messageElement.appendChild(textElement);
        messageArea.appendChild(messageElement);

        messageArea.scrollTop = messageArea.scrollHeight;    
    } catch (error) {
        console.error("Error caught: " + error);
    }
}

function getAvatarColor(messageSender) {
    let hash = 0;

    for (let i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    let index = Math.abs(hash % colors.length);

    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)
