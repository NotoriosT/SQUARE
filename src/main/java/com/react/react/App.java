package com.react.react;


import com.react.react.config.ObjMsg;
import com.react.react.modelo.Enemy;
import com.react.react.modelo.Player;
import com.react.react.modelo.Tabuleiro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@Service
@EnableWebSocketMessageBroker
@EnableScheduling
@ComponentScan(basePackages = "com.react.react")
public class App {
    private  int limite=20;
    private static final Logger logger = LoggerFactory.getLogger(App.class);
List<Tabuleiro> tabuleiro=new ArrayList<>();
List<Enemy> enemies=new ArrayList<>();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/chatMessage")
    @SendTo("/canal")
    public ObjMsg sendMessage(ObjMsg message){

        return message;
    }
    List<Player> players = new ArrayList<>();




    @MessageMapping("/player")
    @SendTo("/canal")
    public Player sendMessage(Player player) {
        for (Player ply : players) {
            if (ply.getId().equalsIgnoreCase(player.getId())) {
                ply.setX(ply.getX() + player.getX());
                ply.setY(ply.getY() + player.getY());

                // Criar uma cópia da lista de inimigos para evitar ConcurrentModificationException
                List<Enemy> enemiesCopy = new ArrayList<>(enemies);

                for (Enemy enemy : enemiesCopy) {
                    if (enemy!=null){
                    if (verificaColisao(ply.getX(), ply.getY(), 30, 30, enemy.getX(), enemy.getY(), 10, 10)) {
                        // Remover o inimigo da lista
                        enemies.remove(enemy);
                        messagingTemplate.convertAndSend("/canal", new ObjMsg("removeEnemy", enemy.getX(), enemy.getY()));
                        logger.info("colidiu com! " + enemy.getX() + "" + enemy.getY());
                    }}
                }

                return ply;
            }
        }
        players.add(player);
        return player;
    }


    @MessageMapping("/spawnEnemy")
    @SendTo("/canal")
    public Enemy spawnEnemy() {
        // Adiciona um novo inimigo à lista de inimigos
        Random random = new Random();
        int x = random.nextInt(800);  // Ajuste conforme necessário
        int y = random.nextInt(600);  // Ajuste conforme necessário

        Enemy enemy = new Enemy();
        enemy.setX(x);
        enemy.setY(y);
        enemies.add(enemy);
        messagingTemplate.convertAndSend("/canal", enemies);
        logger.info("Método createEnemyScheduled chamado!");
        // Envia a lista atualizada de inimigos para todos os clientes
        return enemy ;
    }

    // Agendando a criação de inimigos a cada segundo
    @Scheduled(fixedRate = 5000)
    public void createEnemyScheduled() {
if (limite>enemies.size()){
    spawnEnemy();
}

    }


    private boolean verificaColisao(int x1, int y1, int width1, int height1, int x2, int y2, int width2, int height2) {
        // Coordenadas dos cantos dos retângulos
        int x1Min = x1;
        int x1Max = x1 + width1;
        int y1Min = y1;
        int y1Max = y1 + height1;

        int x2Min = x2;
        int x2Max = x2 + width2;
        int y2Min = y2;
        int y2Max = y2 + height2;

        // Verificar sobreposição
        if (x1Max >= x2Min && x1Min <= x2Max && y1Max >= y2Min && y1Min <= y2Max) {
            // Os retângulos se sobrepõem, ou seja, houve colisão
            return true;
        } else {
            // Não houve colisão
            return false;
        }
    }

}
