# Overview: QueueBroker / MessageQueue

Les MessageQueue représentent un modèle de communication par file d’attente, où des messages de taille variable peuvent être envoyés et reçus. Contrairement aux Channels qui traitent les messages en tant que séquences d’octets, une MessageQueue fonctionne en envoyant et recevant des messages sous forme de « blocs » de taille variable.

Les MessageQueues utilisent une structure FIFO.

## Connecting

La connexion entre deux MessageQueues est similaire à celle entre deux Channels. Le « client » et le « serveur » disposent chacun de MessageQueues.

La connexion utilise les méthodes connectées et acceptez de la classe QueueBroker et suit les mêmes règles de rendez-vous que pour les Channels :

- Si une Task exécute un acceptez sur un port et qu’une seconde appelle connect avec le même port sur un autre QueueBroker, une connexion sera établie.
- Si aucune tâche n’a appelé accept le port spécifié, l’appel connect sera bloqué jusqu’à l’appel d’un accept.

## Sending messages

`void send(byte[] bytes, int offset, int length);`

La méthode send permet d’envoyer des messages sous forme de bloc.

Le message envoyé peut être de taille variable et la méthode est bloquante si la file d’attente n’a pas assez de place pour accepter le message.

## Receiving messages

`byte[] receive();`

L'opération receive permet de recevoir un message. Si aucun message n'est disponible, la méthode bloque jusqu'à ce qu'un message soit envoyé. Une fois disponible, elle retourne un tableau d'octets contenant le message reçu.

## Closing the queue

`void close();`

La méthode close ferme la MessageQueue localement. Après l’appel de cette méthode, aucune autre opération d’envoi ou de réception ne peut être effectuée.
