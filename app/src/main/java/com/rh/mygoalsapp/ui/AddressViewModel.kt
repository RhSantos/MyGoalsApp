package com.rh.mygoalsapp.ui

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rh.mygoalsapp.data.api.RetrofitInstance.api
import com.rh.mygoalsapp.data.models.toAddress
import com.rh.mygoalsapp.data.repository.AddressRepository
import com.rh.mygoalsapp.domain.models.Address
import com.rh.mygoalsapp.util.Validation
import com.rh.mygoalsapp.util.validateAddressInputs
import com.rh.mygoalsapp.util.validateAdressTextFieldsForApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddressViewModel(
    private val addressRepository: AddressRepository
) : ViewModel() {

    fun adressFieldsCompletetionApiCall (
        cepTextFieldValue : MutableState<TextFieldValue>,
        logradouroTextFieldValue : MutableState<TextFieldValue>,
        complementoTextFieldValue : MutableState<TextFieldValue>,
        bairroTextFieldValue : MutableState<TextFieldValue>,
        cidadeTextFieldValue : MutableState<TextFieldValue>
    ) {
        cepTextFieldValue.value.let { cep ->
            val validation = cep.text.split("-")[0].length == 5 && cep.text.split("-")[1].length == 3
            if (cep.text.length == 9 && validation) {

                GlobalScope.launch (Dispatchers.Main) {
                    var addressByCepData = Address.emptyAddress
                    var list = listOf<String>()

                    val job = GlobalScope.launch {
                        val addressByCep = getAdressInfoByPostCode(cep.text)
                        when (addressByCep) {
                            is Validation.Sucess -> {
                                addressByCep.data as Address
                                addressByCepData = addressByCep.data
                                list = validateAdressTextFieldsForApiCall(
                                    mapOf(
                                        logradouroTextFieldValue to "Logradouro",
                                        complementoTextFieldValue to "Complemento",
                                        bairroTextFieldValue to "Bairro",
                                        cidadeTextFieldValue to "Cidade"
                                    )
                                )

                            }
                            else -> {
                                return@launch
                            }
                        }
                    }

                    job.join()

                    addressByCepData.let {
                        list.forEach {
                                el ->
                            when(el) {
                                "Logradouro" -> {
                                    logradouroTextFieldValue.value = logradouroTextFieldValue.value.copy(it.street)
                                }
                                "Complemento" -> {
                                    complementoTextFieldValue.value = complementoTextFieldValue.value.copy(it.complement)
                                }
                                "Bairro" -> {
                                    bairroTextFieldValue.value = bairroTextFieldValue.value.copy(it.district)
                                }
                                "Cidade" -> {
                                    cidadeTextFieldValue.value = cidadeTextFieldValue.value.copy(it.city)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun validateAddress(address: Address,numberValue: String): Validation {
        return validateAddressInputs(address, numberValue)
    }

    suspend fun getAdressInfoByPostCode(cep: String): Validation {
        val address = api.getAddressInfoByPostCode(cep)


        return if (address.isSuccessful) when (address.code()) {
            200 -> {
                when(address.body()?.cep != null) {
                    true -> Validation.Sucess("Informações de Endereço Encontradas!", address.body()!!.toAddress())
                    else -> Validation.Failed("Cep Inexistente")
                }
            }
            else -> Validation.Failed("Erro ao tentar capturar o endereço utilizando o CEP: CEP Inválido!")
        } else Validation.Failed("Erro no serviço")
    }

    class AddressViewModelFactory(
        private val addressRepository: AddressRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddressViewModel(addressRepository) as T
        }
    }
}