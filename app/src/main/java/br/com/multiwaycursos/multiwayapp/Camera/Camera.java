package br.com.multiwaycursos.multiwayapp.Camera;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by rafael on 16/11/2017.
 */

public class Camera extends Activity {
    /**
     * Código de requisição para poder identificar quando a activity da câmera é finalizada
     */
    private static final int REQUEST_PICTURE = 1000;

    /**
     * View onde a foto tirada será exibida
     */
    private ImageView imageView;

    /**
     * Local de armazenamento da foto tirada
     */
    private File imageFile;

    /**
     * Invocado quando a activity é criada
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */

    public Camera(ImageView imageview) {
           this.imageView = imageview;

        // Obtém o local onde as fotos são armazenadas na memória externa do dispositivo
        File picsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        // Define o local completo onde a foto será armazenada (diretório + arquivo)
        this.imageFile = new File(picsDir, "foto.jpg");
    }

    /**
     * Método que tira uma foto
     * @param// v
     */
    public void takePicture() {
        // Cria uma intent que será usada para abrir a aplicação nativa de câmera
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Indica na intent o local onde a foto tirada deve ser armazenada
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));

        // Abre a aplicação de câmera
        startActivityForResult(i, REQUEST_PICTURE);
    }

    /**
     * Método chamado quando a aplicação nativa da câmera é finalizada
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
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
                    imageView.setImageBitmap(picture);
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
}
