/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        
        try {
            conectaDAO conecta = new conectaDAO();
            conn = conecta.connectDB();
            
            PreparedStatement stmt = this.conn.prepareStatement(sql);

            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getValor());
            stmt.setString(3, produto.getStatus());
            stmt.executeUpdate();
            stmt.close();

        } catch (Exception e) {
            System.out.println("Não foi possível cadastrar o produto. Verifique os dados informados." + e.getMessage());
        }
    }
    
    public void venderProduto(int id) {
        String sql = "UPDATE produtos set STATUS = 'Vendido' WHERE id = ?";
        
        try {
            conectaDAO conecta = new conectaDAO();
            conn = conecta.connectDB();
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            stmt.executeUpdate();
            stmt.close();
            
        } catch (Exception e) {
            System.out.println("Erro ao vendar produto: " + e.getMessage());
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        String sql = "SELECT * FROM produtos";
        ArrayList<ProdutosDTO> listaProdutos = new ArrayList<>();
        
        try {
            conectaDAO conecta = new conectaDAO();
            conn = conecta.connectDB();
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                ProdutosDTO produtos = new ProdutosDTO();
                produtos.setId(rs.getInt("id"));
                produtos.setNome(rs.getString("nome"));
                produtos.setValor(rs.getInt("valor"));
                produtos.setStatus(rs.getString("status"));
                
                listaProdutos.add(produtos);
            }
            
            return listaProdutos;
        } catch (Exception e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
            return listaProdutos;
        
        
    
}
    }
    
    public ArrayList<ProdutosDTO> listagemFiltrada (String nome) {
        ArrayList<ProdutosDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE 1=1";
        
        if (nome != null && !nome.trim().isEmpty()) {
            sql += " AND nome LIKE ?";
        }
        
        try {
            conectaDAO conecta = new conectaDAO();
            conn = conecta.connectDB();
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            
            int index = 1;
            if (nome != null && !nome.trim().isEmpty()) {
                stmt.setString(index++, "%" + nome + "%");
            }
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProdutosDTO p = new ProdutosDTO();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setValor(rs.getInt("valor"));
                p.setStatus(rs.getString("status"));
                
                lista.add(p);            
            }
            return lista;
        } catch (Exception e) {
            System.out.println("Erro ao filtrar produtos: " + e.getMessage());
            return lista;
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        ArrayList<ProdutosDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
        
        try {
            conectaDAO conecta = new conectaDAO();
            conn = conecta.connectDB();
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                ProdutosDTO p = new ProdutosDTO();
                
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setValor(rs.getInt("valor"));
                p.setStatus(rs.getString("status"));
                
                lista.add(p);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar produtos vendidos: " + e.getMessage());
        }
        return lista;
    }
}

