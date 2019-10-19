from django.shortcuts import render

from affine_cipher.forms import AffineCipherForm


def index(request):
    return render(request, 'affine_cipher/index.html')


def affine_cipher_encrypt(request):
    """
    Receive a request of type POST or GET
    On POST
        • the input data for encrypting a text is given: text, a, b
        • text is encrypted and returned to the view
    On GET
        • Empty form for the needed input is shown
    :param request: POST or GET Request
    :return: a view holding either an empty form or a filled form together with the encrypted text
    """
    if request.method == "POST":
        form = AffineCipherForm(request.POST)
        if form.is_valid():
            affine_cipher = form.save(commit=False)
            if 'encrypt' in form.data:
                encrypted = affine_cipher.encrypt()
                return render(request, 'affine_cipher/affine_cipher.html', {'form': form, 'encrypted_text': encrypted})
            elif 'decrypt' in form.data:
                decrypted = affine_cipher.decrypt()
                return render(request, 'affine_cipher/affine_cipher.html', {'form': form, 'decrypted_text': decrypted})
    else:
        form = AffineCipherForm()
    return render(request, 'affine_cipher/affine_cipher.html', {'form': form})
