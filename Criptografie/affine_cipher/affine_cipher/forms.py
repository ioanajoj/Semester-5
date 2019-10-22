from django import forms

from affine_cipher.models import AffineCipher


class AffineCipherForm(forms.ModelForm):
    class Meta:
        model = AffineCipher
        fields = ['text', 'a', 'b']

    def __init__(self, *args, **kwargs):
        super(AffineCipherForm, self).__init__(*args, **kwargs)

