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
import java.sql.SQLException;
import java.util.List;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    

    public void cadastrarProduto(ProdutosDTO produto) {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        try {
            conn = new conectaDAO().connectDB();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getValor());
            stmt.setString(3, produto.getStatus());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Salvo com sucesso");
          
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro ao salvar");

        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos() {
         ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        String sql = "SELECT id, nome, valor, status FROM produtos";
        
        try {
            conn = new conectaDAO().connectDB(); // Estabelece a conexão
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            
            while (resultSet.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultSet.getInt("id"));
                produto.setNome(resultSet.getString("nome"));
                produto.setValor(resultSet.getInt("valor"));
                produto.setStatus(resultSet.getString("status"));
                listagem.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        

        return listagem;
   }
    public List<ProdutosDTO> listarProdutosVendidos(){

        try {
            conn = new conectaDAO().connectDB();
            
            String sql;
            sql = "SELECT * FROM uc11.produtos WHERE status = 'Vendido';";
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery(sql);
            
            List<ProdutosDTO> lista_vendido = new ArrayList<>();
            
            while(resultset.next()){
                ProdutosDTO produtosDTO = new ProdutosDTO();
                produtosDTO.setId(resultset.getInt(1));
                produtosDTO.setNome(resultset.getString(2));
                produtosDTO.setValor(resultset.getInt(3));
                produtosDTO.setStatus(resultset.getString(4));

                lista_vendido.add(produtosDTO);
            }
            return lista_vendido;
        } catch (SQLException ex) {
            return null;
        }finally {
            
            try {
                if (resultset != null) {
                    resultset.close();
                }
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
    }    
    }

}
