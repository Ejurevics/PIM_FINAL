package services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;

public class ClienteApiService {

    private static final String BASE_URL = "http://10.0.2.2:8085/api/clientes";
    //private static final String BASE_URL = "http://localhost:8085/api/clientes";
    // URL base para todas as requisições API. O endereço "10.0.2.2" é usado para o Android Emulator acessar o "localhost".

    /**
     * Método getAllClientes()
     *
     * Propósito: Envia uma requisição HTTP GET para obter todos os clientes cadastrados na API.
     *
     * @return List<Cliente> - Uma lista de objetos Cliente representando todos os clientes no banco de dados.
     * @throws Exception - Pode lançar exceções relacionadas a operações de rede e JSON.
     */
    public static List<Cliente> getAllClientes() throws Exception {
        URL url = new URL(BASE_URL + "/listar"); // Cria a URL completa para a operação de listagem.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Abre uma conexão HTTP.
        conn.setRequestMethod("GET"); // Define o método da requisição como GET.
        conn.setRequestProperty("Content-Type", "application/json"); // Define o tipo de conteúdo como JSON.

        // Buffer para ler a resposta da API.
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) { // Lê cada linha da resposta.
            result.append(line);
        }
        reader.close();

        List<Cliente> clientes = new ArrayList<>(); // Lista para armazenar os clientes.
        JSONArray jsonArray = new JSONArray(result.toString()); // Converte a resposta para um array JSON.
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i); // Extrai cada objeto JSON.
            Cliente cliente = new Cliente(); // Cria um novo objeto Cliente.
            cliente.setId(jsonObject.getInt("id")); // Define o ID do cliente.
            cliente.setNome(jsonObject.getString("nome")); // Define o nome do cliente.
            cliente.setEmail(jsonObject.getString("email")); // Define o email do cliente.
            clientes.add(cliente); // Adiciona o cliente à lista.
        }
        return clientes; // Retorna a lista completa de clientes.
    }

    /**
     * Método loginCliente()
     *
     * Propósito: Envia uma requisição HTTP POST para realizar o login de um cliente.
     *
     * @param cliente - Objeto Cliente contendo email e senha para autenticação.
     * @return String - Mensagem indicando sucesso ou falha da operação.
     * @throws Exception - Pode lançar exceções relacionadas a operações de rede e JSON.
     */
    public static String loginCliente(Cliente cliente) throws Exception {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(BASE_URL + "/login"); // Cria a URL para o endpoint de login.
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true); // Permite enviar dados na requisição.

            // Cria um objeto JSON com os dados do cliente.
            JSONObject jsonCliente = new JSONObject();
            jsonCliente.put("email", cliente.getEmail()); // Adiciona o email ao objeto JSON.
            jsonCliente.put("senha", cliente.getSenha()); // Adiciona a senha ao objeto JSON.

            // Envia o JSON com os dados do cliente.
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            writer.write(jsonCliente.toString());
            writer.flush();
            writer.close();

            int responseCode = conn.getResponseCode(); // Obtém o código de resposta da requisição.
            if (responseCode == HttpURLConnection.HTTP_OK) { // Verifica se a resposta foi bem-sucedida.
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                return result.toString(); // Retorna a resposta da API.
            } else {
                return "Erro ao realizar login: " + responseCode; // Retorna mensagem de erro.
            }
        } catch (JSONException e) {
            throw new Exception("Erro ao criar JSON: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new Exception("Erro de IO: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.disconnect(); // Fecha a conexão.
            }
        }
    }

    /**
     * Método addCliente()
     *
     * Propósito: Envia uma requisição HTTP POST para adicionar um novo cliente à API.
     *
     * @param cliente - Objeto Cliente que contém os dados do cliente a ser adicionado.
     * @return String - Resposta da API indicando sucesso ou erro da operação.
     * @throws Exception - Pode lançar exceções relacionadas a operações de rede e JSON.
     */
    public static String addCliente(Cliente cliente) throws Exception {
        URL url = new URL(BASE_URL + "/adicionar"); // Cria a URL completa para a operação de adicionar.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Abre uma conexão HTTP.
        conn.setRequestMethod("POST"); // Define o método da requisição como POST.
        conn.setRequestProperty("Content-Type", "application/json"); // Define o tipo de conteúdo como JSON.
        conn.setDoOutput(true); // Permite enviar dados na requisição.

        // Cria um objeto JSON com os dados do cliente.
        JSONObject jsonCliente = new JSONObject();
        jsonCliente.put("nome", cliente.getNome()); // Adiciona o nome ao objeto JSON.
        jsonCliente.put("telefone", cliente.getTelefone()); // Adiciona o telefone.
        jsonCliente.put("email", cliente.getEmail()); // Adiciona o email ao objeto JSON.
        jsonCliente.put("senha", cliente.getSenha()); // Adiciona a senha ao objeto JSON.
        jsonCliente.put("rua", cliente.getRua()); // Adiciona o campo "rua".
        jsonCliente.put("numero", cliente.getNumero()); // Adiciona o campo "numero".

        // Envia o JSON com os dados do cliente.
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        writer.write(jsonCliente.toString()); // Escreve o JSON no corpo da requisição.
        writer.flush();
        writer.close();

        // Lê a resposta da API.
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) { // Lê cada linha da resposta.
            result.append(line);
        }
        reader.close();
        return result.toString(); // Retorna a resposta da API.
    }


    /**
     * Método updateCliente()
     *
     * Propósito: Envia uma requisição HTTP PUT para atualizar os dados de um cliente existente na API.
     *
     * @param emailOriginal - Email atual do cliente no banco de dados.
     * @param cliente - Objeto Cliente contendo os novos dados do cliente.
     * @return String - Resposta da API indicando sucesso ou erro da operação.
     * @throws Exception - Pode lançar exceções relacionadas a operações de rede e JSON.
     */
    public static String updateCliente(String emailOriginal, Cliente cliente) throws Exception {
        URL url = new URL(BASE_URL + "/atualizar"); // URL genérica para atualizar clientes.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Cria o objeto JSON com os dados atualizados do cliente.
        JSONObject jsonCliente = new JSONObject();
        jsonCliente.put("emailOriginal", emailOriginal); // Corrigido: agora passa o e-mail original
        jsonCliente.put("nome", cliente.getNome()); // Novos dados do cliente.
        jsonCliente.put("telefone", cliente.getTelefone());
        jsonCliente.put("email", cliente.getEmail()); // Novo e-mail.
        jsonCliente.put("senha", cliente.getSenha());

        // Envia o JSON com os dados para a API.
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        writer.write(jsonCliente.toString());
        writer.flush();
        writer.close();

        // Lê a resposta da API.
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();

        // Retorna a resposta da API.
        return result.toString();
    }




    /**
     * Método deleteCliente()
     *
     * Propósito: Envia uma requisição HTTP DELETE para deletar um cliente existente na API.
     *
     //* @param id - ID do cliente a ser deletado.
     * @return String - Resposta da API indicando sucesso ou erro da operação.
     * @throws Exception - Pode lançar exceções relacionadas a operações de rede.
     */
    public static String deleteCliente(String email) throws Exception {
        // URL base para a API com o endpoint ajustado para excluir pelo e-mail
        URL url = new URL(BASE_URL + "/deletar?email=" + email); // Supondo que o endpoint aceite o e-mail como query parameter
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Abre a conexão HTTP
        conn.setRequestMethod("DELETE"); // Define o método como DELETE
        conn.setRequestProperty("Content-Type", "application/json"); // Define o tipo de conteúdo como JSON

        // Lê a resposta da API
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();
        return result.toString(); // Retorna a resposta da API
    }

    /**
     * Método buscarEnderecoCliente()
     *
     * Propósito: Envia uma requisição HTTP GET para buscar o endereço (rua e número) de um cliente usando o e-mail.
     *
     * @param email - E-mail do cliente cujas informações de endereço serão buscadas.
     * @return String - Rua e número do cliente ou mensagem de erro.
     * @throws Exception - Pode lançar exceções relacionadas a operações de rede e JSON.
     */
    public static String buscarEnderecoCliente(String email) throws Exception {
        HttpURLConnection conn = null;
        try {
            // URL do endpoint ajustado para incluir o e-mail como parâmetro de consulta
            URL url = new URL(BASE_URL + "/clientes/endereco?email=" + URLEncoder.encode(email, "UTF-8"));
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            // Verifica o código de resposta
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();

                // Processar o JSON e extrair rua e número
                JSONObject enderecoObj = new JSONObject(result.toString());
                String rua = enderecoObj.getString("rua");
                String numero = enderecoObj.getString("numero");

                // Retornar o endereço formatado
                return rua + ", " + numero;
            } else {
                return "Erro ao buscar endereço: Código de resposta " + responseCode;
            }
        } catch (IOException e) {
            throw new Exception("Erro de IO: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new Exception("Erro ao processar a resposta: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }


}