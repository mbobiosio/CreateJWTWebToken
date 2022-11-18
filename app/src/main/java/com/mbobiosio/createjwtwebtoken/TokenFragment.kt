package com.mbobiosio.createjwtwebtoken

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mbobiosio.createjwtwebtoken.databinding.FragmentTokenBinding
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TokenFragment : Fragment() {

    private var _binding: FragmentTokenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTokenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            textviewToken.text = generateToken()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun generateToken(): String? {
        return Jwts.builder().apply {
            setIssuer(getString(R.string.app_name))
            setIssuedAt(Date())
            setExpiration(getExpiryTime())
            signWith(Keys.hmacShaKeyFor(getSecretKey().toByteArray()), SignatureAlgorithm.HS256)
        }.compact()
    }

    private fun getSecretKey(): String {
        return "your-secret-web-token"
    }

    private fun getExpiryTime(): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_WEEK, 7)
        return calendar.time
    }
}