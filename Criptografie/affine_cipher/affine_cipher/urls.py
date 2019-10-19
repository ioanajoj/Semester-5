from django.urls import path
from affine_cipher import views


app_name = 'affine_cipher'

urlpatterns = [
    path('', views.index, name='index'),
    path('affine-cipher', views.affine_cipher_encrypt, name='affine-cipher')
]
