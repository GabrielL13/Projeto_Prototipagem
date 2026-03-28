#include <WiFi.h>

#include <FirebaseESP32.h>


#define ssid "moto g7" 
#define password "12345678" 
#define FIREBASE_HOST Host
#define FIREBASE_AUTH Senha


FirebaseData firebaseData;

int pinTrava = 14; //declara o pino que será ligado ao modulo relé

void setup() {
  Serial.begin(115200);
  pinMode(pinTrava, OUTPUT); //inicia o pino como saida digital
  delay(1500);
  
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Conectando ao Wi-Fi...");
  }

  Serial.println("Conectado ao Wi-Fi!");

  // Inicialize a conexão com o Firebase
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
}

void loop() {
  if (Firebase.getBool(firebaseData, "/Trancas/0001/porta")) {
    if (firebaseData.boolData()) {
      Serial.println("Porta Aberta");
      abrirTranca();
    }
    else {
      Serial.println("Porta Trancada");
      fecharTranca();
    }
  } else {
    Serial.println("Não foi possivel conectar ao firebase");
  }
}

void abrirTranca() {
  digitalWrite(pinTrava, HIGH);
  delay(1500);
  
}

void fecharTranca(){
  digitalWrite(pinTrava, LOW);
  delay(1500);
}
