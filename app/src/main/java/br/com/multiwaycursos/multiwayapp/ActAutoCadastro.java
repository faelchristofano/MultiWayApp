package br.com.multiwaycursos.multiwayapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.multiwaycursos.multiwayapp.Camera.Camera;
import br.com.multiwaycursos.multiwayapp.Conexao.HttpConnection;
import br.com.multiwaycursos.multiwayapp.model.Usuario;

public class ActAutoCadastro extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText edt_matricula;
    private EditText edt_nome;
    private EditText edt_responsavel;
    private Button btn_solicitar_acesso;

    private SQLiteDatabase db;

    /**
     * Código de requisição para poder identificar quando a activity da câmera é finalizada
     */
    private static final int REQUEST_PICTURE = 1000;

    private static final String BASEURL = "http://192.168.1.103/appMultiWay/process.php";
    private static final String NOME_BANCO = "appMultiWay";
    private static final int VERSAO_BANCO = 1;

    public static final String TABELA = "usuario";
    public static final String ID = "_id";
    public static final String MATRICULA = "matricula";
    public static final String NOME = "nome";
    public static final String RESPONSAVEL = "responsavel";

    public ImageView img;
    private Bitmap bitmap;

    /**
     * Local de armazenamento da foto tirada
     */
    private File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_auto_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edt_matricula = (EditText) findViewById(R.id.edt_matricula);
        edt_nome = (EditText) findViewById(R.id.edt_nome);
        edt_responsavel = (EditText) findViewById(R.id.edt_responsavel);
        btn_solicitar_acesso = (Button) findViewById(R.id.btn_solicitar_acesso);


        db=openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABELA + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MATRICULA + " INTEGER, " +
                NOME + " TEXT NOT NULL, " +
                RESPONSAVEL + " TEXT);");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

       NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.img = (ImageView) findViewById(R.id.imgv_imagem);

        // Obtém o local onde as fotos são armazenadas na memória externa do dispositivo
        File picsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        // Define o local completo onde a foto será armazenada (diretório + arquivo)
        this.imageFile = new File(picsDir, "foto.jpg");
    }

    public void TirarFoto(View view){
        Camera camera = new Camera(this.img);
        camera.takePicture();
    }
    /**
     * Método que tira uma foto
     * @param v
     */
   /* public void takePicture(View v) {
        // Cria uma intent que será usada para abrir a aplicação nativa de câmera
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Indica na intent o local onde a foto tirada deve ser armazenada
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));

        // Abre a aplicação de câmera
        startActivityForResult(i, REQUEST_PICTURE);
    }
*/
  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Verifica o código de requisição e se o resultado é OK (outro resultado indica que
        // o usuário cancelou a tirada da foto)
        if (requestCode == REQUEST_PICTURE && resultCode == RESULT_OK) {

            FileInputStream fis = null;

            try {
                try {
                    // Cria um FileInputStream para ler a foto tirada pela câmera
                    fis = new FileInputStream(imageFile);

                    // Converte a stream em um objeto Bitmap
                    Bitmap picture = BitmapFactory.decodeStream(fis);

                    // Exibe o bitmap na view, para que o usuário veja a foto tirada
                    img.setImageBitmap(picture);
                } finally {
                    if (fis != null) {
                        fis.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static Bitmap resizeImage (Context context, Bitmap bmpOriginal, float newWidth, float newHeight ){
        Bitmap novoBmp = null;

        int w = bmpOriginal.getWidth();
        int h = bmpOriginal.getHeight();

        float densityFactor = context.getResources().getDisplayMetrics().density;
        float novoW = newWidth * densityFactor;
        float novoH = newHeight * densityFactor;

        //calcula escala em percentual do tamanho original para o novo tamanho
        float scalaW = novoW / w;
        float scalaH = novoH / h;

        //criando uma matrix ára manipulação da imagem Bitmap
        Matrix matrix = new Matrix();

        //definindo a proposrção da escola para o matrix
        matrix.postScale(scalaW, scalaH);

        //criando o novo bitimap com o novo tamanho
        novoBmp = Bitmap.createBitmap(bmpOriginal, 0, 0, w, h, matrix, true);

        return novoBmp;


    }*/

    public void btnSolicitarAcessoClick(View view){
        if(edt_matricula.getText().toString().trim().length() == 0 || edt_nome.getText().toString().trim().length() == 0){
            mostrarMensagem("Erro", "Preencha Corretamente os Valores");
            return;
        }

        db.execSQL("INSERT INTO "+ TABELA + " (matricula, nome, responsavel) values ("
                            + Integer.parseInt(edt_matricula.getText().toString()) + ", '"
                            + edt_nome.getText().toString() + "', '"
                            + edt_responsavel.getText().toString() + "')"
        );

        mostrarMensagem("OK", "Dados Gravados com Sucesso");

        sendJson(view);
        limparCampos();



    }

    private void mostrarMensagem(String titulo, String mensagem){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.show();
    }

    private  void limparCampos(){
        edt_matricula.setText("");
        edt_nome.setText("");
        edt_responsavel.setText("");
    }

    public void sendJson(View view){

        Usuario usuario = new Usuario();
        usuario.setMatricula(Integer.parseInt(edt_matricula.getText().toString()));
        usuario.setNome(edt_nome.getText().toString());
        usuario.setResponsavel(edt_responsavel.getText().toString());

        String json = generateJSON(usuario);

        callServer("send-json", json);
    }

    public void getJson(View view){
        callServer("get-json", "");
    }

    private String generateJSON(Usuario usuario){
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();

        try{
            jo.put("matricula", usuario.getMatricula());
            jo.put("nome", usuario.getNome());
            jo.put("responsavel", usuario.getResponsavel());

        }
        catch(JSONException e){ e.printStackTrace(); }

        return(jo.toString());
    }


    private Usuario degenerateJSON(String data){
        Usuario usuario = new Usuario();

        try{
            JSONObject jo = new JSONObject(data);
            JSONArray ja;

            usuario.setMatricula(jo.getInt("matricula"));
            usuario.setMatricula(jo.getInt("nome"));
            usuario.setMatricula(jo.getInt("responsavel"));


            jo.put("matricula", usuario.getMatricula());
            jo.put("nome", usuario.getNome());
            jo.put("responsavel", usuario.getResponsavel());


            // APRESENTAÇÃO
            Log.i("Script", "Matricula: "+usuario.getMatricula());
            Log.i("Script", "Nome: "+usuario.getNome());
            Log.i("Script", "Responsavel: "+usuario.getResponsavel());


        }
        catch(JSONException e){ e.printStackTrace(); }

        return(usuario);
    }



    @SuppressLint("NewApi")
    private void callServer(final String method, final String data){
        new Thread(){
            public void run(){
                String answer = HttpConnection.getSetDataWeb(BASEURL, method, data);

                Log.i("Script", "ANSWER: "+answer);

                if(data.isEmpty()){
                    degenerateJSON(answer);
                }
            }
        }.start();
    }

      @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.act_auto_cadastro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
